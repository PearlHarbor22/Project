package com.bank.history.service;

import com.bank.history.dto.HistoryDto;

import java.util.List;

public interface HistoryService {

    void save(HistoryDto dto);

    List<HistoryDto> getAll();
}
