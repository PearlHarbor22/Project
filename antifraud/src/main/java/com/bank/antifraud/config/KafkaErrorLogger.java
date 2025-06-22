package com.bank.antifraud.config;

import com.bank.antifraud.exception.EmptyReturnedValueException;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.security.oauthbearer.internals.secured.ValidateException;
import org.springframework.kafka.KafkaException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaErrorLogger {
    public void handleEntityNotFoundException(EntityNotFoundException e, Object payload) {
        logError("Сущность не найдена: {}. Параметры запроса: {}", e, payload);
    }

    public void handleValidationException(ValidateException e, Object payload) {
        logError("Ошибка валидации: {}. Объект: {}", e, payload);
    }

    public void handleIllegalArgumentException(IllegalArgumentException e, Object payload) {
        logError("Аргумент не соответствует методу: {}. Аргумент: {}", e, payload);
    }

    public void handleRuntimeException(RuntimeException e, Object payload) {
        logError("Ошибка на стороне сервера: {}. Объект, который привел к ошибке: {}", e, payload);
    }

    public void handleJsonProcessingException(JsonProcessingException e, Object payload) {
        logError("Ошибка записи сущности в JSON: {}. Сущность: {}", e, payload);
    }

    public void handleEmptyReturnedValueException(EmptyReturnedValueException e, Object payload) {
        logError("Метод вернул пустое значение {} Вызывающий объект: {}", e, payload);
    }

    public void handleClassCastException(ClassCastException e, Object payload) {
        logError("Ошибка преобразования: {}, попытка преобразования: {}", e, payload);
    }

    public void handleNullPointerException(NullPointerException e, Object payload) {
        logError("Сущность содержит null: {} Сущность: {}", e, payload);
    }

    public void handleKafkaException(KafkaException e, Object payload) {
        logError("Ошибка Kafka:{};при попытке отправки {}", e, payload);
    }

    public void handleSerializationException(SerializationException e, Object payload) {
        logError("Ошибка сериализации:{};при попытке отправки {}", e, payload);
    }

    public void handleTimeoutException(TimeoutException e, Object payload) {
        logError("Таймаут подключения:{}; при попытке отправки {}", e, payload);
    }

    public void handlePersistenceException(PersistenceException e, Object payload) {
        logError("Ошибка при сохранении:{}, сущность {}", e, payload);
    }

    private void logError(String messageTemplate, Exception e, Object payload) {
        log.error(messageTemplate, e.getMessage(), payload);
    }

}
