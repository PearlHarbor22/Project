package com.bank.publicinfo.kafkaProducer;

import com.bank.publicinfo.config.KafkaErrorLogger;
import com.bank.publicinfo.dto.BankDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BankDetailsProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaErrorLogger kafkaErrorLogger;

    public void sendCreate(BankDetailsDto dto) {
        send("public-info.bank.createResponse", dto);
    }

    public void sendUpdate(BankDetailsDto dto) {
        send("public-info.updateResponse", dto);
    }

    public void sendDelete(BankDetailsDto dto) {
        send("public-info.bank.deleteResponse", dto);
    }

    public void sendGetRequest(BankDetailsDto dto) {
        send("public-info.bank.getResponse", dto);
    }

    private void send(String topic, BankDetailsDto dto) {
        try {
            kafkaTemplate.send(topic, dto);
            log.info("Отправили BankDetailsDto в топик {}: {}", topic, dto);
        } catch (RuntimeException e) {
            kafkaErrorLogger.handleError(e, dto, "Ошибка при отправке сообщения в топик" + topic);
        }
    }
}
