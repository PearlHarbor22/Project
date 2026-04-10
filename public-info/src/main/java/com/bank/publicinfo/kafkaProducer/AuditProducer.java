package com.bank.publicinfo.kafkaProducer;

import com.bank.publicinfo.config.KafkaErrorLogger;
import com.bank.publicinfo.dto.AuditDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaErrorLogger kafkaErrorLogger;

    @Value("${spring.kafka.topic.audit}")
    private String auditTopic;

    public void sendAudit(AuditDto auditDto) {
        try {
            kafkaTemplate.send(auditTopic, auditDto);
            log.info("Аудит отправил в кафку: {}", auditDto);
        } catch (RuntimeException e) {
            kafkaErrorLogger.handleError(e, auditDto, "Ошибка отправки аудита в Kafka");
        }
    }
}
