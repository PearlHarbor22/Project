package com.bank.publicinfo.services;

import com.bank.publicinfo.dto.LicenseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LicenseService {

    LicenseDto create(LicenseDto dto);

    LicenseDto update(Long id, LicenseDto dto);

    void delete(Long id);

    LicenseDto getById(Long id);

    Page<LicenseDto> getAll(Pageable pageable);
}
