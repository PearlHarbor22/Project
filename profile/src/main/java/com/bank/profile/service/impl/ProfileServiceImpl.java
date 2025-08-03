package com.bank.profile.service.impl;

import com.bank.profile.aop.Auditable;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ActualRegistration;
import com.bank.profile.entity.Passport;
import com.bank.profile.entity.Profile;
import com.bank.profile.kafka.producer.ProfileProducer;
import com.bank.profile.mapper.ProfileMapper;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.service.ProfileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация сервиса {@link com.bank.profile.service.ProfileService} для управления данными профиля пользователя.
 * Содержит бизнес-логику создания, обновления, получения и удаления профилей.
 * Также отправляет события в Kafka по факту создания и удаления.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final ProfileProducer kafkaProducer;

    /**
     * Создаёт новый профиль.
     * Если DTO содержит ID, он сбрасывается для предотвращения конфликта идентификаторов.
     * После сохранения профиль отправляется в Kafka.
     *
     * @param dto DTO с данными профиля
     * @return сохранённый профиль
     */
    @Override
    @Auditable
    @Transactional
    public ProfileDto save(ProfileDto dto) {
        if (dto.getId() != null) {
            log.warn("DTO содержит id = {}, обнуляем для корректного создания", dto.getId());
            dto.setId(null);
        }
        Profile entity = profileMapper.toEntity(dto);
        Profile saved = profileRepository.save(entity);
        ProfileDto savedDto = profileMapper.toDto(saved);
        kafkaProducer.sendCreate(savedDto);
        return savedDto;
    }

    /**
     * Обновляет существующий профиль.
     * Обновление происходит по ID, данные берутся из DTO.
     * Также может обновить связанные сущности: паспорт и адрес.
     *
     * @param id  идентификатор профиля
     * @param dto DTO с новыми данными
     * @return обновлённый профиль
     * @throws EntityNotFoundException если профиль не найден
     */
    @Override
    @Auditable
    @Transactional
    public ProfileDto update(Long id, ProfileDto dto) {
        Profile existing = profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Профиль не найден: id = " + id));

        Profile updated = profileMapper.toEntity(dto);
        updated.setId(existing.getId());

        if (dto.getPassportId() != null) {
            updated.setPassport(Passport.builder().id(dto.getPassportId()).build());
        }
        if (dto.getActualRegistrationId() != null) {
            updated.setActualRegistration(ActualRegistration.builder().id(dto.getActualRegistrationId()).build());
        }

        return profileMapper.toDto(profileRepository.save(updated));
    }

    /**
     * Получает профиль по идентификатору.
     *
     * @param id идентификатор профиля
     * @return найденный профиль
     * @throws EntityNotFoundException если профиль не найден
     */
    @Override
    @Transactional(readOnly = true)
    public ProfileDto findById(Long id) {
        return profileRepository.findById(id)
                .map(profileMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Профиль не найден: id = " + id));
    }

    /**
     * Возвращает список всех профилей с пагинацией.
     *
     * @param pageable параметры страницы
     * @return страница с DTO профилей
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProfileDto> findAll(Pageable pageable) {
        return profileRepository.findAll(pageable).map(profileMapper::toDto);
    }

    /**
     * Удаляет профиль по идентификатору.
     * После удаления отправляет событие в Kafka.
     *
     * @param id идентификатор удаляемого профиля
     * @throws EntityNotFoundException если профиль не найден
     */
    @Override
    @Transactional
    public void delete(Long id) {
        try {
            profileRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Профиль не найден: id = " + id);
        }

        ProfileDto dto = new ProfileDto();
        dto.setId(id);
        kafkaProducer.sendDelete(dto);
    }
}

