package com.bank.history.service;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import com.bank.history.mapper.HistoryMapper;
import com.bank.history.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Реализация сервиса для управления историей изменений.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository repository;
    private final HistoryMapper mapper;

    /**
     * Сохраняет новую запись истории.
     */
    @Override
    public void save(HistoryDto dto) {
        HistoryEntity entity = mapper.toEntity(dto);
        repository.save(entity);
        log.info("История успешно сохранена: {}", entity);
    }

    /**
     * Возвращает список всех записей истории.
     */
    @Override
    public List<HistoryDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }
}
