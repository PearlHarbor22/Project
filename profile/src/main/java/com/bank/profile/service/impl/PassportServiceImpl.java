package com.bank.profile.service.impl;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.Passport;
import com.bank.profile.entity.Registration;
import com.bank.profile.mapper.PassportMapper;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.service.PassportService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassportServiceImpl implements PassportService {

    private final PassportRepository passportRepository;
    private final PassportMapper passportMapper;

    @Override
    @Transactional
    public PassportDto save(PassportDto dto) {
        Passport entity = passportMapper.toEntity(dto);

        if (dto.getRegistrationId() != null) {
            entity.setRegistration(Registration.builder().id(dto.getRegistrationId()).build());
        }

        return passportMapper.toDto(passportRepository.save(entity));
    }

    @Override
    @Transactional
    public PassportDto update(Long id, PassportDto dto) {
        Passport existing = passportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Паспорт не найден: id = " + id));

        Passport updated = passportMapper.toEntity(dto);
        updated.setId(existing.getId());

        if (dto.getRegistrationId() != null) {
            updated.setRegistration(Registration.builder().id(dto.getRegistrationId()).build());
        }

        return passportMapper.toDto(passportRepository.save(updated));
    }

    @Override
    public PassportDto findById(Long id) {
        return passportRepository.findById(id)
                .map(passportMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Паспорт не найден: id = " + id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!passportRepository.existsById(id)) {
            throw new EntityNotFoundException("Паспорт не найден: id = " + id);
        }
        passportRepository.deleteById(id);
    }

    @Override
    public List<PassportDto> findAll() {
        return passportRepository.findAll().stream()
                .map(passportMapper::toDto)
                .toList();
    }
}
