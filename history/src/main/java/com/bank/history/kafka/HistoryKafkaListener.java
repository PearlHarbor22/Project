package com.bank.history.kafka;

import com.bank.history.dto.HistoryDto;
import com.bank.history.handler.KafkaErrorLogger;
import com.bank.history.service.HistoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class HistoryKafkaListener {

    private final HistoryService service;
    private final KafkaErrorLogger exceptionHandler;

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
