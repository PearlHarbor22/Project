package com.bank.profile.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "custom-kafka")
@Getter
@Setter
public class KafkaTopicMappingProperties {
    private Map<String, String> topicMapping;
}
