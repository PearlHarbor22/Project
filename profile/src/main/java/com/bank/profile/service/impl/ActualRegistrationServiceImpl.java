package com.bank.profile.service.impl;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistration;
import com.bank.profile.mapper.ActualRegistrationMapper;
import com.bank.profile.repository.ActualRegistrationRepository;
import com.bank.profile.service.ActualRegistrationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActualRegistrationServiceImpl implements ActualRegistrationService {

    private final ActualRegistrationRepository actualRegistrationRepository;
    private final ActualRegistrationMapper actualRegistrationMapper;

    @Override
    @Transactional
    public ActualRegistrationDto save(ActualRegistrationDto dto) {
        ActualRegistration entity = actualRegistrationMapper.toEntity(dto);
        return actualRegistrationMapper.toDto(actualRegistrationRepository.save(entity));
    }

    @Override
    @Transactional
    public ActualRegistrationDto update(Long id, ActualRegistrationDto dto) {
        ActualRegistration existing = actualRegistrationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Фактическая регистрация не найдена: id=" + id));

        ActualRegistration updated = actualRegistrationMapper.toEntity(dto);
        updated.setId(existing.getId());

        return actualRegistrationMapper.toDto(actualRegistrationRepository.save(updated));
    }

    @Override
    public ActualRegistrationDto findById(Long id) {
        return actualRegistrationRepository.findById(id)
                .map(actualRegistrationMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Фактическая регистрация не найдена: id=" + id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!actualRegistrationRepository.existsById(id)) {
            throw new EntityNotFoundException("Фактическая регистрация не найдена: id=" + id);
        }
        actualRegistrationRepository.deleteById(id);
    }

    @Override
    public List<ActualRegistrationDto> findAll() {
        return actualRegistrationRepository.findAll().stream()
                .map(actualRegistrationMapper::toDto)
                .toList();
    }
}
