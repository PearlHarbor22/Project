package com.bank.publicinfo.kafkaListener;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.services.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificateListener {
    private final CertificateService service;

    @KafkaListener(topics = "public-info.certificate.create", groupId = "${spring.kafka.consumer.group-id}")
    public void create(CertificateDto dto) {
        service.create(dto);
    }

    @KafkaListener(topics = "public-info.certificate.update", groupId = "${spring.kafka.consumer.group-id}")
    public void update(CertificateDto dto) {
        service.update(dto.getId(), dto);
    }

    @KafkaListener(topics = "public-info.certificate.delete", groupId = "${spring.kafka.consumer.group-id}")
    public void delete(CertificateDto dto) {
        service.delete(dto.getId());
    }
}
