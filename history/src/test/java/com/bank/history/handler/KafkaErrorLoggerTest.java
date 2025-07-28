package com.bank.history.handler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;

public class KafkaErrorLoggerTest {

    private final KafkaErrorLogger errorLogger = new KafkaErrorLogger();

    @Test
    void handleEntityNotFoundException_DoesNotThrow() {
        EntityNotFoundException ex = new EntityNotFoundException("Не найдена сущность");
        Object payload = "payload_data";
        errorLogger.handleEntityNotFoundException(ex, payload);
    }

    @Test
    void handleValidationException_DoesNotThrow() {
        ValidationException ex = new ValidationException("Ошибка валидации");
        Object payload = 42;
        errorLogger.handleValidationException(ex, payload);
    }

    @Test
    void handleProcessingError_DoesNotThrow() {
        Exception ex = new Exception("Что-то пошло не так");
        Object payload = null;
        errorLogger.handleProcessingError(ex, payload);
    }
}