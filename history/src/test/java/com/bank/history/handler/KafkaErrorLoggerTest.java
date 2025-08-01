package com.bank.history.handler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;

import static com.bank.history.util.TestData.NOT_FOUND_MESSAGE;
import static com.bank.history.util.TestData.VALIDATION_ERROR_MESSAGE;
import static com.bank.history.util.TestData.PROCESSING_ERROR_MESSAGE;
import static com.bank.history.util.TestData.PAYLOAD_DATA;
import static com.bank.history.util.TestData.PAYLOAD_NUMBER;

public class KafkaErrorLoggerTest {

    private final KafkaErrorLogger errorLogger = new KafkaErrorLogger();

    @Test
    void handleEntityNotFoundException_DoesNotThrow() {
        Object payload = PAYLOAD_DATA;
        EntityNotFoundException ex = new EntityNotFoundException(NOT_FOUND_MESSAGE);

        errorLogger.handleEntityNotFoundException(ex, payload);
    }

    @Test
    void handleValidationException_DoesNotThrow() {
        Object payload = PAYLOAD_NUMBER;
        ValidationException ex = new ValidationException(VALIDATION_ERROR_MESSAGE);

        errorLogger.handleValidationException(ex, payload);
    }

    @Test
    void handleProcessingError_DoesNotThrow() {
        Object payload = null;
        Exception ex = new Exception(PROCESSING_ERROR_MESSAGE);

        errorLogger.handleProcessingError(ex, payload);
    }
}