package com.bank.profile.service;

import com.bank.profile.dto.ActualRegistrationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ActualRegistrationService {

    ActualRegistrationDto save(ActualRegistrationDto dto);

    ActualRegistrationDto update(Long id, ActualRegistrationDto dto);

    ActualRegistrationDto findById(Long id);

    Page<ActualRegistrationDto> findAll(Pageable pageable);

    void delete(Long id);

}
