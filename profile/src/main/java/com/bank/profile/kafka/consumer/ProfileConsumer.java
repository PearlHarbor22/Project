package com.bank.profile.kafka.consumer;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.service.ProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProfileConsumer {

    private final ProfileService profileService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "${app.kafka.profile.create-topic}",
            groupId = "${app.kafka.profile.group-id}"
    )
    public void handleCreate(ConsumerRecord<String, Object> record) {
        Object value = record.value();
        try {
            ProfileDto dto = objectMapper.convertValue(value, ProfileDto.class);
            log.info("Получено создание профиля: {}", dto);
            profileService.save(dto);
        } catch (Exception ex) {
            log.error("Ошибка при обработке создания профиля: {}", value, ex);
        }
    }

    @KafkaListener(
            topics = "${app.kafka.profile.update-topic}",
            groupId = "${app.kafka.profile.group-id}"
    )
    public void handleUpdate(ConsumerRecord<String, Object> record) {
        Object value = record.value();
        try {
            ProfileDto dto = objectMapper.convertValue(value, ProfileDto.class);
            log.info("Получено обновление профиля: {}", dto);
            profileService.update(dto.getId(), dto);
        } catch (Exception ex) {
            log.error("Ошибка при обработке обновления профиля: {}", value, ex);
        }
    }

    @KafkaListener(
            topics = "${app.kafka.profile.delete-topic}",
            groupId = "${app.kafka.profile.group-id}"
    )
    public void handleDelete(ConsumerRecord<String, Object> record) {
        Object value = record.value();
        try {
            ProfileDto dto = objectMapper.convertValue(value, ProfileDto.class);
            log.info("Получено удаление профиля с id: {}", dto.getId());
            profileService.delete(dto.getId());
        } catch (Exception ex) {
            log.error("Ошибка при обработке удаления профиля: {}", value, ex);
        }
    }

    @KafkaListener(
            topics = "${app.kafka.profile.get-topic}",
            groupId = "${app.kafka.profile.group-id}"
    )
    public void handleGet(ConsumerRecord<String, Object> record) {
        Object value = record.value();
        try {
            ProfileDto dto = objectMapper.convertValue(value, ProfileDto.class);
            log.info("Получен запрос на получение профиля с id: {}", dto.getId());
            profileService.findById(dto.getId());
        } catch (Exception ex) {
            log.error("Ошибка при обработке запроса профиля: {}", value, ex);
        }
    }
}
