package com.bank.antifraud.service;

import com.bank.antifraud.config.KafkaErrorLogger;
import com.bank.antifraud.entity.SuspiciousPhoneTransfer;
import com.bank.antifraud.repository.SuspiciousPhoneTransferRepository;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuspiciousPhoneTransferService implements SuspiciousTransferSaveService<SuspiciousPhoneTransfer> {

    private final SuspiciousPhoneTransferRepository suspiciousPhoneTransferRepository;
    private final KafkaErrorLogger kafkaErrorLogger;

    public void save(SuspiciousPhoneTransfer suspiciousPhoneTransfer) {
        try {
            suspiciousPhoneTransferRepository.save(suspiciousPhoneTransfer);
        } catch (PersistenceException e) {
            kafkaErrorLogger.handlePersistenceException(e, suspiciousPhoneTransfer);
        } catch (IllegalArgumentException e) {
            kafkaErrorLogger.handleIllegalArgumentException(e, suspiciousPhoneTransfer);
        } catch (RuntimeException e) {
            kafkaErrorLogger.handleRuntimeException(e, suspiciousPhoneTransfer);
        }
    }
}
