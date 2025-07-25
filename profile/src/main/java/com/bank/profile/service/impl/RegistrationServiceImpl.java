package com.bank.profile.service.impl;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.Registration;
import com.bank.profile.mapper.RegistrationMapper;
import com.bank.profile.repository.RegistrationRepository;
import com.bank.profile.service.RegistrationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final RegistrationMapper registrationMapper;

    @Override
    @Transactional
    public RegistrationDto save(RegistrationDto dto) {
        Registration entity = registrationMapper.toEntity(dto);
        Registration saved = registrationRepository.save(entity);
        return registrationMapper.toDto(saved);
    }

    @Override
    @Transactional
    public RegistrationDto update(Long id, RegistrationDto dto) {
        Registration existing = registrationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Регистрация не найдена: id = " + id));

        Registration updated = registrationMapper.toEntity(dto);
        updated.setId(existing.getId());

        return registrationMapper.toDto(registrationRepository.save(updated));
    }

    @Override
    public RegistrationDto findById(Long id) {
        return registrationRepository.findById(id)
                .map(registrationMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Регистрация не найдена: id = " + id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!registrationRepository.existsById(id)) {
            throw new EntityNotFoundException("Регистрация не найдена: id = " + id);
        }
        registrationRepository.deleteById(id);
    }

    @Override
    public List<RegistrationDto> findAll() {
        return registrationRepository.findAll().stream()
                .map(registrationMapper::toDto)
                .toList();
    }
}
