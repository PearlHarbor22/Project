package com.bank.antifraud.kafka.producer;

import com.bank.antifraud.config.KafkaErrorLogger;
import com.bank.antifraud.dto.AuditDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditProducer {
    @Value("${spring.kafka.producer.audit-topic}")
    private String auditTopic;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaErrorLogger kafkaErrorLogger;

    public void sendAudit(AuditDto auditDto) {
        try {
            kafkaTemplate.send(auditTopic, auditDto);
            log.info("Аудит отправлен в топик {}", auditTopic);
        } catch (SerializationException e) {
            kafkaErrorLogger.handleSerializationException(e, auditDto);
        } catch (TimeoutException e) {
            kafkaErrorLogger.handleTimeoutException(e, auditDto);
        } catch (KafkaException e) {
            kafkaErrorLogger.handleKafkaException(e, auditDto);
        } catch (RuntimeException e) {
            kafkaErrorLogger.handleRuntimeException(e, auditDto);
        }
    }
}
