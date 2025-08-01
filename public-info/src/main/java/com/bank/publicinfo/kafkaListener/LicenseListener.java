package com.bank.publicinfo.kafkaListener;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.services.LicenseService;
import lombok.RequiredArgsConstructor;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LicenseListener {
    private final LicenseService service;

    @KafkaListener(topics = "public-info.license.create", groupId = "${spring.kafka.consumer.group-id}")
    public void create(LicenseDto dto) {
        service.create(dto);
    }

    @KafkaListener(topics = "public-info.license.update", groupId = "${spring.kafka.consumer.group-id}")
    public void update(LicenseDto dto) {
        service.update(dto.getId(), dto);
    }

    @KafkaListener(topics = "public-info.license.delete", groupId = "${spring.kafka.consumer.group-id}")
    public void delete(LicenseDto dto) {
        service.delete(dto.getId());
    }
}
