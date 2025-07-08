package com.bank.history.handler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GlobalExceptionHandler {
    public void handleEntityNotFoundException(EntityNotFoundException ex, Object payload) {
        log.error("EntityNotFoundException: Не найдена сущность. Payload: {}, Причина: {}", payload, ex.getMessage(), ex);
    }

    public void handleValidationException(ValidationException ex, Object payload) {
        log.warn("ValidationException: Ошибка валидации данных. Payload: {}, Причина: {}", payload, ex.getMessage());
    }

    public void handleProcessingError(Exception ex, Object payload) {
        log.error("Unexpected error: Необработанная ошибка при обработке Kafka-сообщения. Payload: {}, Ошибка: {}", payload, ex.getMessage(), ex);
    }
}
