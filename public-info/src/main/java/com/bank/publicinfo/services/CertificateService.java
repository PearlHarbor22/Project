package com.bank.publicinfo.services;

import com.bank.publicinfo.dto.CertificateDto;
import java.util.List;

public interface CertificateService {

    CertificateDto create(CertificateDto dto);

    CertificateDto update(Long id, CertificateDto dto);

    void delete(Long id);

    CertificateDto getById(Long id);

    List<CertificateDto> getAll();
}
