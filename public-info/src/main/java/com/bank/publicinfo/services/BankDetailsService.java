package com.bank.publicinfo.services;

import com.bank.publicinfo.dto.BankDetailsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BankDetailsService {

    BankDetailsDto create(BankDetailsDto dto);

    BankDetailsDto update(Long id, BankDetailsDto dto);

    void delete(Long id);

    BankDetailsDto getById(Long id);

    Page<BankDetailsDto> getAll(Pageable pageable);
}
