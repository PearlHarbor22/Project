package com.bank.publicinfo.services;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entities.BankDetails;
import com.bank.publicinfo.entities.License;
import com.bank.publicinfo.Mappers.LicenseMapper;
import com.bank.publicinfo.repositories.BankDetailsRepository;
import com.bank.publicinfo.repositories.LicenseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {
    private final LicenseRepository licenseRepository;
    private final BankDetailsRepository bankDetailsRepository;
    private final LicenseMapper licenseMapper;

    @Transactional
    @Override
    public LicenseDto create(LicenseDto dto) {
        License license = licenseMapper.toEntity(dto);

        BankDetails bank = bankDetailsRepository.findById(dto.getBankDetailsId())
                .orElseThrow(() -> new EntityNotFoundException("Банк с id " + dto.getBankDetailsId() + " не найден"));
        license.setBankDetails(bank);

        return licenseMapper.toDto(licenseRepository.save(license));
    }

    @Transactional
    @Override
    public LicenseDto update(Long id, LicenseDto dto) {
        License existing = licenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Лицензия с id " + id + " не найдена"));

        existing.setPhoto(dto.getPhoto());

        Long existingBankId = Optional.ofNullable(existing.getBankDetails())
                .map(BankDetails::getId)
                .orElse(null);

        if (!Objects.equals(existingBankId, dto.getBankDetailsId())) {
            BankDetails newBank = bankDetailsRepository.findById(dto.getBankDetailsId())
                    .orElseThrow(() -> new EntityNotFoundException("Банк с id " + dto.getBankDetailsId() + " не найден"));
            existing.setBankDetails(newBank);
        }

        return licenseMapper.toDto(licenseRepository.save(existing));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        License license = licenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Лицензия с id " + id + " не найдена"));

        licenseRepository.delete(license);
    }

    @Transactional(readOnly = true)
    @Override
    public LicenseDto getById(Long id) {
        return licenseRepository.findById(id)
                .map(licenseMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Лицензия с id " + id + " не найдена"));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<LicenseDto> getAll(Pageable pageable) {
        return licenseRepository.findAll(pageable)
                .map(licenseMapper::toDto);
    }
}
