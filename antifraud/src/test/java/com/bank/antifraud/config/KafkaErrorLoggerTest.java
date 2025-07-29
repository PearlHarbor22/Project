package com.bank.antifraud.config;

import com.bank.antifraud.exception.EmptyReturnedValueException;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.security.oauthbearer.internals.secured.ValidateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.KafkaException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static com.bank.antifraud.util.Constants.TEST_EXCEPTION_MESSAGE;

@ExtendWith(MockitoExtension.class)
class KafkaErrorLoggerTest {
    @InjectMocks
    KafkaErrorLogger kafkaErrorLogger;

    @Test
    void handleEntityNotFoundException_success() {
        EntityNotFoundException exception = new EntityNotFoundException(TEST_EXCEPTION_MESSAGE);
        Object payload = new Object();

        assertDoesNotThrow(() -> kafkaErrorLogger.handleEntityNotFoundException(exception, payload));
    }

    @Test
    void handleValidationException_success() {
        ValidateException exception = new ValidateException(TEST_EXCEPTION_MESSAGE);
        Object payload = new Object();

        assertDoesNotThrow(() -> kafkaErrorLogger.handleValidationException(exception, payload));
    }

    @Test
    void handleIllegalArgumentException_success() {
        IllegalArgumentException exception = new IllegalArgumentException(TEST_EXCEPTION_MESSAGE);
        Object payload = new Object();

        assertDoesNotThrow(() -> kafkaErrorLogger.handleIllegalArgumentException(exception, payload));
    }

    @Test
    void handleRuntimeException_success() {
        RuntimeException exception = new RuntimeException(TEST_EXCEPTION_MESSAGE);
        Object payload = new Object();

        assertDoesNotThrow(() -> kafkaErrorLogger.handleRuntimeException(exception, payload));
    }

    @Test
    void handleJsonProcessingException_success() {
        JsonProcessingException exception = new JsonProcessingException(TEST_EXCEPTION_MESSAGE) {
        };
        Object payload = new Object();

        assertDoesNotThrow(() -> kafkaErrorLogger.handleJsonProcessingException(exception, payload));
    }

    @Test
    void handleEmptyReturnedValueException_success() {
        EmptyReturnedValueException exception = new EmptyReturnedValueException(TEST_EXCEPTION_MESSAGE);
        Object payload = new Object();

        assertDoesNotThrow(() -> kafkaErrorLogger.handleEmptyReturnedValueException(exception, payload));
    }

    @Test
    void handleClassCastException_success() {
        ClassCastException exception = new ClassCastException(TEST_EXCEPTION_MESSAGE);
        Object payload = new Object();

        assertDoesNotThrow(() -> kafkaErrorLogger.handleClassCastException(exception, payload));
    }

    @Test
    void handleNullPointerException_success() {
        NullPointerException exception = new NullPointerException(TEST_EXCEPTION_MESSAGE);
        Object payload = new Object();

        assertDoesNotThrow(() -> kafkaErrorLogger.handleNullPointerException(exception, payload));
    }

    @Test
    void handleKafkaException_success() {
        KafkaException exception = new KafkaException(TEST_EXCEPTION_MESSAGE);
        Object payload = new Object();

        assertDoesNotThrow(() -> kafkaErrorLogger.handleKafkaException(exception, payload));
    }

    @Test
    void handleSerializationException_success() {
        SerializationException exception = new SerializationException(TEST_EXCEPTION_MESSAGE);
        Object payload = new Object();

        assertDoesNotThrow(() -> kafkaErrorLogger.handleSerializationException(exception, payload));
    }

    @Test
    void handleTimeoutException_success() {
        TimeoutException exception = new TimeoutException(TEST_EXCEPTION_MESSAGE);
        Object payload = new Object();

        assertDoesNotThrow(() -> kafkaErrorLogger.handleTimeoutException(exception, payload));
    }

    @Test
    void handlePersistenceException_success() {
        PersistenceException exception = new PersistenceException(TEST_EXCEPTION_MESSAGE);
        Object payload = new Object();

        assertDoesNotThrow(() -> kafkaErrorLogger.handlePersistenceException(exception, payload));
    }
} 