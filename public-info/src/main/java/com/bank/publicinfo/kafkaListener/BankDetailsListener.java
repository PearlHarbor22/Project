package com.bank.publicinfo.kafkaListener;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.services.BankDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BankDetailsListener {
    private final BankDetailsService service;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "public-info.bank.create", groupId = "${spring.kafka.consumer.group-id}")
    public void create(BankDetailsDto dto) {
        log.info("Получено сообщение: {}", dto);
        service.create(dto);
    }
    @KafkaListener(topics = "public-info.bank.createResponse", groupId = "${spring.kafka.consumer.group-id}")
    public void receiveCreateResponse(BankDetailsDto dto) {
        log.info("Получен ответ о создании банка: {}", dto);
    }

    @KafkaListener(topics = "public-info.bank.update", groupId = "${spring.kafka.consumer.group-id}")
    public void update(BankDetailsDto dto) {
        service.update(dto.getId(), dto);
    }

    @KafkaListener(topics = "public-info.bank.updateResponse", groupId = "${spring.kafka.consumer.group-id}")
    public void receiveDeleteResponse(BankDetailsDto dto) {
        log.info("Получен ответ о обновлении информации банка: {}", dto);
    }

    @KafkaListener(topics = "public-info.bank.delete", groupId = "${spring.kafka.consumer.group-id}")
    public void delete(BankDetailsDto dto) {
        service.delete(dto.getId());
    }

    @KafkaListener(topics = "public-info.bank.deleteResponse", groupId = "${spring.kafka.consumer.group-id}")
    public void receiveUpdateResponse(BankDetailsDto dto) {
        log.info("Получен ответ о удалении информации банка: {}", dto);
    }

    @KafkaListener(topics = "public-info.bank.get", groupId = "${spring.kafka.consumer.group-id}")
    public void get(BankDetailsDto dto) {
        BankDetailsDto found = service.getById(dto.getId());
    }

    @KafkaListener(topics = "account.bank.get", groupId = "${spring.kafka.consumer.group-id}")
    public void getFromAccountService(BankDetailsDto dto) {
        BankDetailsDto found = service.getById(dto.getId());
        kafkaTemplate.send("account.bank.response", found);
    }

    @KafkaListener(topics = "history.bank.get", groupId = "${spring.kafka.consumer.group-id}")
    public void getFromHistoryService(BankDetailsDto dto) {
        BankDetailsDto found = service.getById(dto.getId());
        kafkaTemplate.send("history.bank.response", found);
    }
}
