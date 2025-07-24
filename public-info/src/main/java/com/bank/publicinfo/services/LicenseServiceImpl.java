package com.bank.publicinfo.services;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entities.BankDetails;
import com.bank.publicinfo.entities.License;
import com.bank.publicinfo.Mappers.LicenseMapper;
import com.bank.publicinfo.repositories.BankDetailsRepository;
import com.bank.publicinfo.repositories.LicenseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LicenseServiceImpl implements LicenseService {
    private final LicenseRepository licenseRepository;
    private final BankDetailsRepository bankDetailsRepository;
    private final LicenseMapper licenseMapper;

    @Override
    public LicenseDto create(LicenseDto dto) {
        License license = licenseMapper.toEntity(dto);

        BankDetails bank = bankDetailsRepository.findById(dto.getBankDetailsId())
                .orElseThrow(() -> new EntityNotFoundException("Банк с id " + dto.getBankDetailsId() + " не найден"));
        license.setBankDetails(bank);

        return licenseMapper.toDto(licenseRepository.save(license));
    }

    @Override
    public LicenseDto update(Long id, LicenseDto dto) {
        License existing = licenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Лицензия с id " + id + " не найдена"));

        existing.setPhoto(dto.getPhoto());

        if (!existing.getBankDetails().getId().equals(dto.getBankDetailsId())) {
            BankDetails newBank = bankDetailsRepository.findById(dto.getBankDetailsId())
                    .orElseThrow(() -> new EntityNotFoundException("Банк с id " + dto.getBankDetailsId() + " не найден"));
            existing.setBankDetails(newBank);
        }

        return licenseMapper.toDto(licenseRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!licenseRepository.existsById(id)) {
            throw new EntityNotFoundException("Лицензия с id " + id + " не найдена");
        }
        licenseRepository.deleteById(id);
    }

    @Override
    public LicenseDto getById(Long id) {
        return licenseRepository.findById(id)
                .map(licenseMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Лицензия с id " + id + " не найдена"));
    }

    @Override
    public List<LicenseDto> getAll() {
        return licenseRepository.findAll()
                .stream()
                .map(licenseMapper::toDto)
                .collect(Collectors.toList());
    }
}
