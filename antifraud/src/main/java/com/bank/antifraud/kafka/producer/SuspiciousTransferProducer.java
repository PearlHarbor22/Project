package com.bank.antifraud.kafka.producer;

import com.bank.antifraud.config.KafkaErrorLogger;
import com.bank.antifraud.dto.SuspiciousTransferDto;
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
public class SuspiciousTransferProducer {

    @Value("${spring.kafka.producer.suspicious-transfer-topic}")
    private String suspiciousTransferTopic;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaErrorLogger kafkaErrorLogger;

    public void sendSuspiciousTransfer(SuspiciousTransferDto suspiciousTransferDto) {
        try {
            log.info("Аудит отправлен в топик {}", suspiciousTransferTopic);
            kafkaTemplate.send(suspiciousTransferTopic, suspiciousTransferDto);
        } catch (SerializationException e) {
            kafkaErrorLogger.handleSerializationException(e, suspiciousTransferDto);
        } catch (TimeoutException e) {
            kafkaErrorLogger.handleTimeoutException(e, suspiciousTransferDto);
        } catch (KafkaException e) {
            kafkaErrorLogger.handleKafkaException(e, suspiciousTransferDto);
        } catch (RuntimeException e) {
            kafkaErrorLogger.handleRuntimeException(e, suspiciousTransferDto);
        }
    }
}
