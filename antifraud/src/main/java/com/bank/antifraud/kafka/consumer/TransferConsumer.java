package com.bank.antifraud.kafka.consumer;

import com.bank.antifraud.config.KafkaErrorLogger;
import com.bank.antifraud.dto.AccountTransferDto;
import com.bank.antifraud.dto.CardTransferDto;
import com.bank.antifraud.dto.PhoneTransferDto;
import com.bank.antifraud.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor

public class TransferConsumer {

    private final TransferService transferService;
    private final KafkaErrorLogger kafkaErrorLogger;

    @KafkaListener(topics = "transfer.account",
            properties = {"spring.json.value.default.type=com.bank.antifraud.dto.AccountTransferDto"})
    public void consumerAccount(AccountTransferDto accountTransferDto) {
        try {
            log.info("Account transfer считан: " + accountTransferDto);
            transferService.process(accountTransferDto);
        } catch (ClassCastException e) {
            kafkaErrorLogger.handleClassCastException(e, accountTransferDto);
        } catch (NullPointerException e) {
            kafkaErrorLogger.handleNullPointerException(e, accountTransferDto);
        } catch (RuntimeException e) {
            kafkaErrorLogger.handleRuntimeException(e, accountTransferDto);
        }
    }

    @KafkaListener(topics = "transfer.card",
            properties = {"spring.json.value.default.type=com.bank.antifraud.dto.CardTransferDto"})
    public void consumerCard(CardTransferDto cardTransferDto) {
        try {
            log.info("Card transfer считан: " + cardTransferDto);
            transferService.process(cardTransferDto);
        } catch (ClassCastException e) {
            kafkaErrorLogger.handleClassCastException(e, cardTransferDto);
        } catch (NullPointerException e) {
            kafkaErrorLogger.handleNullPointerException(e, cardTransferDto);
        } catch (RuntimeException e) {
            kafkaErrorLogger.handleRuntimeException(e, cardTransferDto);
        }
    }

    @KafkaListener(topics = "transfer.phone",
            properties = {"spring.json.value.default.type=com.bank.antifraud.dto.PhoneTransferDto"})
    public void consumerPhone(PhoneTransferDto phoneTransferDto) {
        try {
            log.info("Phone transfer считан: " + phoneTransferDto);
            transferService.process(phoneTransferDto);
        } catch (ClassCastException e) {
            kafkaErrorLogger.handleClassCastException(e, phoneTransferDto);
        } catch (NullPointerException e) {
            kafkaErrorLogger.handleNullPointerException(e, phoneTransferDto);
        } catch (RuntimeException e) {
            kafkaErrorLogger.handleRuntimeException(e, phoneTransferDto);
        }
    }
}
