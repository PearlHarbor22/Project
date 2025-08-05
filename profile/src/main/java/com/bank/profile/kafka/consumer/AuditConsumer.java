package com.bank.profile.kafka.consumer;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.mapper.AuditMapper;
import com.bank.profile.repository.AuditRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditConsumer {

    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "${app.kafka.audit.topic}",
            groupId = "${app.kafka.audit.group-id}"
    )
    public void listen(ConsumerRecord<String, Object> record) {
        Object value = record.value();
        try {
            AuditDto dto = objectMapper.convertValue(value, AuditDto.class);
            var entity = auditMapper.toEntity(dto);
            auditRepository.save(entity);
            log.info("Audit-сообщение успешно сохранено: {}", dto);
        } catch (Exception ex) {
            log.error("Ошибка при обработке audit-сообщения: {}", value, ex);
        }
    }
}
