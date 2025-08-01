package com.bank.publicinfo.services;

import com.bank.publicinfo.dto.AtmDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AtmService {

    AtmDto create(AtmDto dto);

    AtmDto update(Long id, AtmDto dto);

    void delete(Long id);

    AtmDto getById(Long id);

    Page<AtmDto> getAll(Pageable pageable);
}
