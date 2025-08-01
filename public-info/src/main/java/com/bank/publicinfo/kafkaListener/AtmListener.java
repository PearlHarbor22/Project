package com.bank.publicinfo.kafkaListener;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.services.AtmService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AtmListener {
    private final AtmService service;

    @KafkaListener(topics = "public-info.atm.create", groupId = "${spring.kafka.consumer.group-id}")
    public void create(AtmDto dto) {
        service.create(dto);
    }

    @KafkaListener(topics = "public-info.atm.update", groupId = "${spring.kafka.consumer.group-id}")
    public void update(AtmDto dto) {
        service.update(dto.getId(), dto);
    }

    @KafkaListener(topics = "public-info.atm.delete", groupId = "${spring.kafka.consumer.group-id}")
    public void delete(AtmDto dto) {
        service.delete(dto.getId());
    }
}
