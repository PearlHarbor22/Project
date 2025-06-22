package com.bank.antifraud.service;

import com.bank.antifraud.config.KafkaErrorLogger;
import com.bank.antifraud.entity.SuspiciousCardTransfer;
import com.bank.antifraud.repository.SuspiciousCardTransferRepository;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuspiciousCardTransferService implements SuspiciousTransferSaveService<SuspiciousCardTransfer> {

    private final SuspiciousCardTransferRepository suspiciousCardTransferRepository;
    private final KafkaErrorLogger kafkaErrorLogger;

    public void save(SuspiciousCardTransfer suspiciousCardTransfer) {
        try {
            suspiciousCardTransferRepository.save(suspiciousCardTransfer);
        } catch (PersistenceException e) {
            kafkaErrorLogger.handlePersistenceException(e, suspiciousCardTransfer);
        } catch (IllegalArgumentException e) {
            kafkaErrorLogger.handleIllegalArgumentException(e, suspiciousCardTransfer);
        } catch (RuntimeException e) {
            kafkaErrorLogger.handleRuntimeException(e, suspiciousCardTransfer);
        }
    }
}
