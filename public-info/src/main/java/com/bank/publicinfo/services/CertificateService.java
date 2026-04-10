package com.bank.publicinfo.services;

import com.bank.publicinfo.dto.CertificateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CertificateService {

    CertificateDto create(CertificateDto dto);

    CertificateDto update(Long id, CertificateDto dto);

    void delete(Long id);

    CertificateDto getById(Long id);

    Page<CertificateDto> getAll(Pageable pageable);
}
