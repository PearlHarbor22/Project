package com.bank.publicinfo.services;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entities.BankDetails;
import com.bank.publicinfo.Mappers.BankDetailsMapper;
import com.bank.publicinfo.kafkaProducer.BankDetailsProducer;
import com.bank.publicinfo.repositories.BankDetailsRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для управления банковскими реквизитами.
 * Обеспечивает создание, обновление, удаление и получение данных о банках,
 * а также отправку соответствующих событий через Kafka.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BankDetailsServiceImpl implements BankDetailsService {
    private final BankDetailsRepository repository;
    private final BankDetailsMapper mapper;
    private final BankDetailsProducer bankDetailsProducer;

    /**
     * Создаёт новые банковские реквизиты.
     * Сохраняет сущность, преобразует обратно в DTO и отправляет событие через Kafka.
     *
     * @param dto DTO банковских реквизитов
     * @return созданный DTO
     */
    @Transactional
    @Override
    public BankDetailsDto create(BankDetailsDto dto) {
        BankDetails entity = mapper.toEntity(dto);
        BankDetails saved = repository.save(entity);
        BankDetailsDto result = mapper.toDto(saved);
        bankDetailsProducer.sendCreate(result);
        return result;
    }

    /**
     * Обновляет банковские реквизиты по ID.
     * Если сущность не найдена — выбрасывает исключение.
     * Сохраняет обновлённую сущность и отправляет событие Kafka.
     *
     * @param id ID сущности
     * @param dto DTO с новыми данными
     * @return обновлённый DTO
     */
    @Transactional
    @Override
    public BankDetailsDto update(Long id, BankDetailsDto dto) {
        BankDetails existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Банк с id " + id + " не найден"));

        existing.setBik(dto.getBik());
        existing.setInn(dto.getInn());
        existing.setKpp(dto.getKpp());
        existing.setCorAccount(dto.getCorAccount());
        existing.setCity(dto.getCity());
        existing.setJointStockCompany(dto.getJointStockCompany());
        existing.setName(dto.getName());
        BankDetails updated = repository.save(existing);
        BankDetailsDto result = mapper.toDto(updated);
        bankDetailsProducer.sendUpdate(result);
        return result;
    }

    /**
     * Удаляет банковские реквизиты по ID.
     * Если сущность не найдена — выбрасывает исключение.
     * Отправляет событие Kafka об удалении.
     *
     * @param id ID банковских реквизитов
     */
    @Transactional
    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Банк с id " + id + " не найден");
        }
        repository.deleteById(id);
        BankDetailsDto dto = new BankDetailsDto();
        dto.setId(id);
        bankDetailsProducer.sendDelete(dto);
    }

    /**
     * Получает банковские реквизиты по ID.
     *
     * @param id ID сущности
     * @return найденный DTO
     */
    @Override
    public BankDetailsDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Банк с id " + id + " не найден"));
    }

    /**
     * Получает список всех банковских реквизитов.
     *
     * @return список DTO
     */
    @Override
    public List<BankDetailsDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
