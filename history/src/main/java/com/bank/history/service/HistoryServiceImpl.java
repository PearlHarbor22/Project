package com.bank.history.service;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import com.bank.history.mapper.HistoryMapper;
import com.bank.history.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Реализация сервиса управления историей изменений (аудитом бизнес-событий).
 *
 * <p>
 * Получает события аудита из Kafka от других микросервисов системы банка,
 * преобразует их в сущности и сохраняет в базу данных истории изменений.
 * Также предоставляет доступ ко всем сохранённым записям.
 * </p>
 *
 * <p><b>Источники аудита:</b></p>
 * <ul>
 *     <li><b>transfer</b> — операции перевода по счёту, карте, телефону</li>
 *     <li><b>profile</b> — регистрация пользователей и паспортные данные</li>
 *     <li><b>account</b> — создание и обновление учётных записей</li>
 *     <li><b>authorization</b> — управление пользователями и ролями</li>
 *     <li><b>anti-fraud</b> — блокировка подозрительных операций</li>
 *     <li><b>public-info</b> — изменения публичной информации о банке</li>
 * </ul>
 *
 * <p><b>Принцип работы:</b></p>
 * <ul>
 *     <li>Получает {@link com.bank.history.dto.HistoryDto} из KafkaListener</li>
 *     * <li>Преобразует DTO в {@link com.bank.history.entity.HistoryEntity}<br>
 *     * через {@link com.bank.history.mapper.HistoryMapper}</li>
 *     <li>Сохраняет сущность в базу данных с помощью {@link com.bank.history.repository.HistoryRepository}</li>
 *     <li>Позволяет получить все сохранённые записи истории</li>
 * </ul>
 *
 * <p><b>Архитектурные особенности:</b></p>
 * <ul>
 *     <li>Следует принципу единой ответственности (SRP)</li>
 *     <li>Не содержит логики Kafka, сериализации и валидации</li>
 *     <li>Может быть протестирован независимо от Kafka</li>
 * </ul>
 *
 * @see com.bank.history.kafka.HistoryKafkaListener
 * @see com.bank.history.dto.HistoryDto
 * @see com.bank.history.entity.HistoryEntity
 * @see com.bank.history.repository.HistoryRepository
 * @see com.bank.history.mapper.HistoryMapper
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository repository;
    private final HistoryMapper mapper;

    /**
     * Сохраняет новую запись истории изменений.
     *
     * @param dto объект истории, полученный от Kafka, подлежащий сохранению
     */
    @Override
    public void save(HistoryDto dto) {
        if (Objects.isNull(dto)) {
            log.warn("Передан пустой объект HistoryDto — сохранение невозможно");
            return;
        }

        try {
            final HistoryEntity entity = mapper.toEntity(dto);
            repository.save(entity);
            log.info("История успешно сохранена: {}", entity);
        } catch (Exception e) {
            log.error("Ошибка при сохранении истории: {}", e.getMessage(), e);
        }
    }

    /**
     * Получает список всех записей истории из базы данных.
     *
     * @return список {@link HistoryDto} для дальнейшего использования или отображения
     */
    @Override
    public List<HistoryDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }
}
