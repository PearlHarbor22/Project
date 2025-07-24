package com.bank.publicinfo.services;

import com.bank.publicinfo.dto.BankDetailsDto;
import java.util.List;

public interface BankDetailsService {

    BankDetailsDto create(BankDetailsDto dto);

    BankDetailsDto update(Long id, BankDetailsDto dto);

    void delete(Long id);

    BankDetailsDto getById(Long id);

    List<BankDetailsDto> getAll();
}
