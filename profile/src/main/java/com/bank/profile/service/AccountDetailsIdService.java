package com.bank.profile.service;

import com.bank.profile.dto.AccountDetailsIdDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AccountDetailsIdService {

    AccountDetailsIdDto save(AccountDetailsIdDto dto);

    AccountDetailsIdDto update(Long id, AccountDetailsIdDto dto);

    AccountDetailsIdDto findById(Long id);

    Page<AccountDetailsIdDto> findAll(Pageable pageable);

    void delete(Long id);
} 