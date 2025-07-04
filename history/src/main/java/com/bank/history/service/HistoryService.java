package com.bank.history.service;

import com.bank.history.dto.HistoryDto;

import java.util.List;

/**
 * Сервис для управления историей изменений.
 */
public interface HistoryService {
    /**
     * Сохраняет новую запись истории.
     *
     * @param dto объект истории для сохранения
     */
    void save(HistoryDto dto);

    /**
     * Получает список всех записей истории.
     *
     * @return список DTO объектов истории
     */
    List<HistoryDto> getAll();
}
