package com.bank.profile.service;

import com.bank.profile.dto.RegistrationDto;

import java.util.List;

public interface RegistrationService {

    RegistrationDto save(RegistrationDto dto);

    RegistrationDto update(Long id, RegistrationDto dto);

    RegistrationDto findById(Long id);

    void delete(Long id);

    List<RegistrationDto> findAll();
}
