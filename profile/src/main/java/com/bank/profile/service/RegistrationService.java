package com.bank.profile.service;

import com.bank.profile.dto.RegistrationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RegistrationService {

    RegistrationDto save(RegistrationDto dto);

    RegistrationDto update(Long id, RegistrationDto dto);

    RegistrationDto findById(Long id);

    void delete(Long id);

    Page<RegistrationDto> findAll(Pageable pageable);

}
