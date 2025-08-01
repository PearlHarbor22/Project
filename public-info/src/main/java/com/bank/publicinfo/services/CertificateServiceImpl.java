package com.bank.publicinfo.services;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entities.BankDetails;
import com.bank.publicinfo.entities.Certificate;
import com.bank.publicinfo.Mappers.CertificateMapper;
import com.bank.publicinfo.repositories.BankDetailsRepository;
import com.bank.publicinfo.repositories.CertificateRepository;
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
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final BankDetailsRepository bankDetailsRepository;
    private final CertificateMapper certificateMapper;

    @Transactional
    @Override
    public CertificateDto create(CertificateDto dto) {
        Certificate certificate = certificateMapper.toEntity(dto);

        BankDetails bank = bankDetailsRepository.findById(dto.getBankDetailsId())
                .orElseThrow(() -> new EntityNotFoundException("Банк с id " + dto.getBankDetailsId() + " не найден"));
        certificate.setBankDetails(bank);

        return certificateMapper.toDto(certificateRepository.save(certificate));
    }

    @Transactional
    @Override
    public CertificateDto update(Long id, CertificateDto dto) {
        Certificate existing = certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Сертификат с id " + id + " не найден"));

        existing.setPhoto(dto.getPhoto());

        Long existingBankId = Optional.ofNullable(existing.getBankDetails())
                .map(BankDetails::getId)
                .orElse(null);

        if (!Objects.equals(existingBankId, dto.getBankDetailsId())) {
            BankDetails newBank = bankDetailsRepository.findById(dto.getBankDetailsId())
                    .orElseThrow(() -> new EntityNotFoundException("Банк с id " + dto.getBankDetailsId() + " не найден"));
            existing.setBankDetails(newBank);
        }

        return certificateMapper.toDto(certificateRepository.save(existing));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Сертификат с id " + id + " не найден"));

        certificateRepository.delete(certificate);
    }

    @Transactional(readOnly = true)
    @Override
    public CertificateDto getById(Long id) {
        return certificateRepository.findById(id)
                .map(certificateMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Сертификат с id " + id + " не найден"));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CertificateDto> getAll(Pageable pageable) {
        return certificateRepository.findAll(pageable)
                .map(certificateMapper::toDto);
    }
}
