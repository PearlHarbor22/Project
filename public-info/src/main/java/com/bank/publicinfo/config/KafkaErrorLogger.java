package com.bank.publicinfo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaErrorLogger {
    public void handleError(Exception e, Object payload, String errorType) {
        String payloadType = (payload != null) ? payload.getClass().getSimpleName() : "null";
        String payloadContent = (payload != null) ? payload.toString() : "null";

        log.error("{}: {}. Тип объекта: {}, содержимое: {}", errorType, e.getMessage(), payloadType, payloadContent, e);
    }
}
