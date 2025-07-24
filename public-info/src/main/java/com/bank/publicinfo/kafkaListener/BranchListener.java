package com.bank.publicinfo.kafkaListener;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.services.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BranchListener {
    private final BranchService service;

    @KafkaListener(topics = "public-info.branch.create", groupId = "${spring.kafka.consumer.group-id}")
    public void create(BranchDto dto) {
        service.create(dto);
    }

    @KafkaListener(topics = "public-info.branch.update", groupId = "${spring.kafka.consumer.group-id}")
    public void update(BranchDto dto) {
        service.update(dto.getId(), dto);
    }

    @KafkaListener(topics = "public-info.branch.delete", groupId = "${spring.kafka.consumer.group-id}")
    public void delete(BranchDto dto) {
        service.delete(dto.getId());
    }
}
