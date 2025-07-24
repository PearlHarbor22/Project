package com.bank.publicinfo.services;

import com.bank.publicinfo.dto.LicenseDto;
import java.util.List;

public interface LicenseService {

    LicenseDto create(LicenseDto dto);

    LicenseDto update(Long id, LicenseDto dto);

    void delete(Long id);

    LicenseDto getById(Long id);

    List<LicenseDto> getAll();
}
