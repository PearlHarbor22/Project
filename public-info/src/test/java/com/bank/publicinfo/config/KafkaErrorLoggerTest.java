package com.bank.publicinfo.config;

import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class KafkaErrorLoggerTest {

    private KafkaErrorLogger logger;
    private LogCaptor logCaptor;

    private static final String ERROR_TYPE_PROCESSING = "ProcessingError";
    private static final String ERROR_TYPE_KAFKA = "KafkaError";
    private static final String ERROR_MESSAGE_TEST = "Test error";
    private static final String ERROR_MESSAGE_NULL = "Another error";
    private static final String PAYLOAD_STRING = "Test payload";
    private static final String PAYLOAD_TYPE_STRING = "String";
    private static final String NULL_STRING = "null";

    @BeforeEach
    void setup() {
        logger = new KafkaErrorLogger();
        logCaptor = LogCaptor.forClass(KafkaErrorLogger.class);
    }

    @Test
    void handleError_shouldLogErrorWithPayload() {
        Exception exception = new RuntimeException(ERROR_MESSAGE_TEST);

        logger.handleError(exception, PAYLOAD_STRING, ERROR_TYPE_PROCESSING);

        assertThat(logCaptor.getLogs()).anyMatch(log ->
                log.contains(ERROR_TYPE_PROCESSING + ": " + ERROR_MESSAGE_TEST) &&
                        log.contains("Тип объекта: " + PAYLOAD_TYPE_STRING) &&
                        log.contains("содержимое: " + PAYLOAD_STRING)
        );
    }

    @Test
    void handleError_shouldLogErrorWithNullPayload() {
        Exception exception = new RuntimeException(ERROR_MESSAGE_NULL);

        logger.handleError(exception, null, ERROR_TYPE_KAFKA);

        assertThat(logCaptor.getLogs()).anyMatch(log ->
                log.contains(ERROR_TYPE_KAFKA + ": " + ERROR_MESSAGE_NULL) &&
                        log.contains("Тип объекта: " + NULL_STRING) &&
                        log.contains("содержимое: " + NULL_STRING)
        );
    }
}