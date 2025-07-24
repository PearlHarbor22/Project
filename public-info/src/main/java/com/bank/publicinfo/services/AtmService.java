package com.bank.publicinfo.services;

import com.bank.publicinfo.dto.AtmDto;
import java.util.List;

public interface AtmService {

    AtmDto create(AtmDto dto);

    AtmDto update(Long id, AtmDto dto);

    void delete(Long id);

    AtmDto getById(Long id);

    List<AtmDto> getAll();
}
