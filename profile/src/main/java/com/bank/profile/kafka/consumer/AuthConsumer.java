package com.bank.profile.kafka.consumer;

import com.bank.profile.dto.AuthDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Getter
public class AuthConsumer {

    private final ObjectMapper objectMapper;
    private Long currentProfileId;

    @KafkaListener(topics = "${app.kafka.auth.topic}", groupId = "${app.kafka.auth.group-id}")
    public void listen(ConsumerRecord<String, Object> record) {
        log.info("Получено сообщение (raw): {}", record);

        try {
            Object value = record.value();
            AuthDto dto = objectMapper.convertValue(value, AuthDto.class);

            log.info("Преобразовано в DTO: {}", dto);
            this.currentProfileId = dto.getId();
        } catch (Exception e) {
            log.error("Ошибка при десериализации AuthDto", e);
        }
    }
}
