package com.bank.profile.service;

import com.bank.profile.dto.AccountDetailsIdDto;

import java.util.List;

public interface AccountDetailsIdService {
    AccountDetailsIdDto save(AccountDetailsIdDto dto);

    AccountDetailsIdDto update(Long id, AccountDetailsIdDto dto);

    AccountDetailsIdDto findById(Long id);

    List<AccountDetailsIdDto> findAll();

    void delete(Long id);
} 