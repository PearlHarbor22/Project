package com.bank.profile.kafka.producer;

import com.bank.profile.dto.ProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileProducer {

    private final KafkaTemplate<String, ProfileDto> kafkaTemplate;

    @Value("${app.kafka.profile.create-topic}")
    private String createTopic;

    @Value("${app.kafka.profile.delete-topic}")
    private String deleteTopic;

    public void sendCreate(ProfileDto dto) {
        kafkaTemplate.send(createTopic, dto);
    }

    public void sendDelete(ProfileDto dto) {
        kafkaTemplate.send(deleteTopic, dto);
    }
}
