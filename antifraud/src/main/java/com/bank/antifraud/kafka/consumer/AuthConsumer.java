package com.bank.antifraud.kafka.consumer;

import com.bank.antifraud.config.KafkaErrorLogger;
import com.bank.antifraud.dto.AuthDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
@Slf4j
public class AuthConsumer {
    private Long currentAuthId;
    private final KafkaErrorLogger kafkaErrorLogger;

    @KafkaListener(topics = "auth.users",
            properties = {
                    "spring.json.value.default.type=com.bank.antifraud.dto.AuthDto"})
    public void listen(AuthDto authDto) {
        try {
            this.currentAuthId = authDto.getId();
            log.info("Auth ID: {}", this.currentAuthId);
        } catch (ClassCastException e) {
            kafkaErrorLogger.handleClassCastException(e, authDto.getId());
        } catch (NullPointerException e) {
            kafkaErrorLogger.handleNullPointerException(e, authDto);
        } catch (RuntimeException e) {
            kafkaErrorLogger.handleRuntimeException(e, authDto);
        }
    }
}
