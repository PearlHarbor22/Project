package com.bank.history.kafka;


import com.bank.history.dto.HistoryDto;
import com.bank.history.handler.GlobalExceptionHandler;
import com.bank.history.service.HistoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka-слушатель для получения сообщений из разных микросервисов аудита.
 * Сохраняет ID аудита в базу данных истории.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HistoryKafkaListener {

    private final HistoryService service;
    private final GlobalExceptionHandler exceptionHandler;

    /**
     * Обработка сообщений из микросервиса transfer.
     */
    @KafkaListener(topics = "audit-transfer", groupId = "history")
    public void listenTransfer(HistoryDto dto) {
        try {
            log.info("Получено сообщение из Kafka (transfer): {}", dto);
            service.save(dto);
        } catch (EntityNotFoundException ex) {
            exceptionHandler.handleEntityNotFoundException(ex, dto);
        } catch (ValidationException ex) {
            exceptionHandler.handleValidationException(ex, dto);
        } catch (Exception ex) {
            exceptionHandler.handleProcessingError(ex, dto);
        }
    }

    /**
     * Обработка сообщений из микросервиса profile.
     */
    @KafkaListener(topics = "audit-profile", groupId = "history")
    public void listenProfile(HistoryDto dto) {
        try {
            log.info("Получено сообщение из Kafka (profile): {}", dto);
            service.save(dto);
        } catch (EntityNotFoundException ex) {
            exceptionHandler.handleEntityNotFoundException(ex, dto);
        } catch (ValidationException ex) {
            exceptionHandler.handleValidationException(ex, dto);
        } catch (Exception ex) {
            exceptionHandler.handleProcessingError(ex, dto);
        }
    }

    /**
     * Обработка сообщений из микросервиса account.
     */
    @KafkaListener(topics = "audit-account", groupId = "history")
    public void listenAccount(HistoryDto dto) {
        try {
            log.info("Получено сообщение из Kafka (account): {}", dto);
            service.save(dto);
        } catch (EntityNotFoundException ex) {
            exceptionHandler.handleEntityNotFoundException(ex, dto);
        } catch (ValidationException ex) {
            exceptionHandler.handleValidationException(ex, dto);
        } catch (Exception ex) {
            exceptionHandler.handleProcessingError(ex, dto);
        }
    }

    /**
     * Обработка сообщений из микросервиса anti-fraud.
     */
    @KafkaListener(topics = "audit-antifraud", groupId = "history")
    public void listenAntiFraud(HistoryDto dto) {
        try {
            log.info("Получено сообщение из Kafka (anti-fraud): {}", dto);
            service.save(dto);
        } catch (EntityNotFoundException ex) {
            exceptionHandler.handleEntityNotFoundException(ex, dto);
        } catch (ValidationException ex) {
            exceptionHandler.handleValidationException(ex, dto);
        } catch (Exception ex) {
            exceptionHandler.handleProcessingError(ex, dto);
        }
    }

    /**
     * Обработка сообщений из микросервиса public-bank-info.
     */
    @KafkaListener(topics = "audit-public-info", groupId = "history")
    public void listenPublicBankInfo(HistoryDto dto) {
        try {
            log.info("Получено сообщение из Kafka (public-bank-info): {}", dto);
            service.save(dto);
        } catch (EntityNotFoundException ex) {
            exceptionHandler.handleEntityNotFoundException(ex, dto);
        } catch (ValidationException ex) {
            exceptionHandler.handleValidationException(ex, dto);
        } catch (Exception ex) {
            exceptionHandler.handleProcessingError(ex, dto);
        }
    }

    /**
     * Обработка сообщений из микросервиса authorization.
     */
    @KafkaListener(topics = "audit-authorization", groupId = "history")
    public void listenAuthorization(HistoryDto dto) {
        try {
            log.info("Получено сообщение из Kafka (authorization): {}", dto);
            service.save(dto);
        } catch (EntityNotFoundException ex) {
            exceptionHandler.handleEntityNotFoundException(ex, dto);
        } catch (ValidationException ex) {
            exceptionHandler.handleValidationException(ex, dto);
        } catch (Exception ex) {
            exceptionHandler.handleProcessingError(ex, dto);
        }
    }
}
