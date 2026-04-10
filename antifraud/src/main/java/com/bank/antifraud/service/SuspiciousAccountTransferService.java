package com.bank.antifraud.service;

import com.bank.antifraud.config.KafkaErrorLogger;
import com.bank.antifraud.entity.SuspiciousAccountTransfer;
import com.bank.antifraud.repository.SuspiciousAccountTransferRepository;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuspiciousAccountTransferService implements SuspiciousTransferSaveService<SuspiciousAccountTransfer> {

    private final SuspiciousAccountTransferRepository suspiciousAccountTransferRepository;
    private final KafkaErrorLogger kafkaErrorLogger;

    public void save(SuspiciousAccountTransfer suspiciousAccountTransfer) {
        try {
            suspiciousAccountTransferRepository.save(suspiciousAccountTransfer);
        } catch (PersistenceException e) {
            kafkaErrorLogger.handlePersistenceException(e, suspiciousAccountTransfer);
        } catch (IllegalArgumentException e) {
            kafkaErrorLogger.handleIllegalArgumentException(e, suspiciousAccountTransfer);
        } catch (RuntimeException e) {
            kafkaErrorLogger.handleRuntimeException(e, suspiciousAccountTransfer);
        }
    }
}
