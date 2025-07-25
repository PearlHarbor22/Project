package com.bank.profile.service;

import com.bank.profile.dto.ActualRegistrationDto;

import java.util.List;

public interface ActualRegistrationService {

    ActualRegistrationDto save(ActualRegistrationDto dto);

    ActualRegistrationDto update(Long id, ActualRegistrationDto dto);

    ActualRegistrationDto findById(Long id);

    void delete(Long id);

    List<ActualRegistrationDto> findAll();
}
