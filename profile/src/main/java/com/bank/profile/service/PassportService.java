package com.bank.profile.service;

import com.bank.profile.dto.PassportDto;

import java.util.List;

public interface PassportService {

    PassportDto save(PassportDto dto);

    PassportDto update(Long id, PassportDto dto);

    PassportDto findById(Long id);

    void delete(Long id);

    List<PassportDto> findAll();
}
