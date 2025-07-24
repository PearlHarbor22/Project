package com.bank.publicinfo.services;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entities.BankDetails;
import com.bank.publicinfo.entities.Certificate;
import com.bank.publicinfo.Mappers.CertificateMapper;
import com.bank.publicinfo.repositories.BankDetailsRepository;
import com.bank.publicinfo.repositories.CertificateRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final BankDetailsRepository bankDetailsRepository;
    private final CertificateMapper certificateMapper;

    @Override
    public CertificateDto create(CertificateDto dto) {
        Certificate certificate = certificateMapper.toEntity(dto);

        BankDetails bank = bankDetailsRepository.findById(dto.getBankDetailsId())
                .orElseThrow(() -> new EntityNotFoundException("Банк с id " + dto.getBankDetailsId() + " не найден"));
        certificate.setBankDetails(bank);

        return certificateMapper.toDto(certificateRepository.save(certificate));
    }

    @Override
    public CertificateDto update(Long id, CertificateDto dto) {
        Certificate existing = certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Сертификат с id " + id + " не найден"));

        existing.setPhoto(dto.getPhoto());

        if (!existing.getBankDetails().getId().equals(dto.getBankDetailsId())) {
            BankDetails newBank = bankDetailsRepository.findById(dto.getBankDetailsId())
                    .orElseThrow(() -> new EntityNotFoundException("Банк с id " + dto.getBankDetailsId() + " не найден"));
            existing.setBankDetails(newBank);
        }

        return certificateMapper.toDto(certificateRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!certificateRepository.existsById(id)) {
            throw new EntityNotFoundException("Сертификат с id " + id + " не найден");
        }
        certificateRepository.deleteById(id);
    }

    @Override
    public CertificateDto getById(Long id) {
        return certificateRepository.findById(id)
                .map(certificateMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Сертификат с id " + id + " не найден"));
    }

    @Override
    public List<CertificateDto> getAll() {
        return certificateRepository.findAll()
                .stream()
                .map(certificateMapper::toDto)
                .collect(Collectors.toList());
    }

}
