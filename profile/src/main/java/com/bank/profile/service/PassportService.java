package com.bank.profile.service;

import com.bank.profile.dto.PassportDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PassportService {

    PassportDto save(PassportDto dto);

    PassportDto update(Long id, PassportDto dto);

    PassportDto findById(Long id);

    void delete(Long id);

    Page<PassportDto> findAll(Pageable pageable);

}
