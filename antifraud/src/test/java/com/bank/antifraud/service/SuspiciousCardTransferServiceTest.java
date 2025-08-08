package com.bank.antifraud.service;

import com.bank.antifraud.config.KafkaErrorLogger;
import com.bank.antifraud.entity.SuspiciousCardTransfer;
import com.bank.antifraud.repository.SuspiciousCardTransferRepository;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bank.antifraud.util.Constants.TEST_BLOCKED_REASON;
import static com.bank.antifraud.util.Constants.TEST_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE;
import static com.bank.antifraud.util.Constants.TEST_PERSISTENT_EXCEPTION_MESSAGE;
import static com.bank.antifraud.util.Constants.TEST_RUNTIME_EXCEPTION_MESSAGE;
import static com.bank.antifraud.util.Constants.TEST_SUSPICIOUS_REASON;
import static com.bank.antifraud.util.Constants.TEST_TRANSFER_ID;
import static com.bank.antifraud.util.ValidEntitiesCreator.getSuspiciousCardTransfer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SuspiciousCardTransferServiceTest {
    @Mock
    KafkaErrorLogger kafkaErrorLogger;
    @Mock
    SuspiciousCardTransferRepository suspiciousCardTransferRepository;
    @InjectMocks
    SuspiciousCardTransferService suspiciousCardTransferService;

    @Test
    public void saveSuccess() {
        SuspiciousCardTransfer suspiciousCardTransfer = getSuspiciousCardTransfer(TEST_TRANSFER_ID, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);

        suspiciousCardTransferRepository.save(suspiciousCardTransfer);

        verify(suspiciousCardTransferRepository).save(suspiciousCardTransfer);
        verifyNoInteractions(kafkaErrorLogger);
    }

    @Test
    public void safeWithPersistenceException() {
        SuspiciousCardTransfer suspiciousCardTransfer = getSuspiciousCardTransfer(TEST_TRANSFER_ID, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);
        PersistenceException persistenceException = new PersistenceException(TEST_PERSISTENT_EXCEPTION_MESSAGE);
        doThrow(persistenceException)
                .when(suspiciousCardTransferRepository)
                .save(suspiciousCardTransfer);

        suspiciousCardTransferService.save(suspiciousCardTransfer);

        verify(suspiciousCardTransferRepository).save(suspiciousCardTransfer);
        verify(kafkaErrorLogger).handlePersistenceException(persistenceException, suspiciousCardTransfer);
        verifyNoMoreInteractions(kafkaErrorLogger);
    }


    @Test
    public void safeWithIllegalArgumentException() {
        SuspiciousCardTransfer suspiciousCardTransfer = getSuspiciousCardTransfer(TEST_TRANSFER_ID, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(TEST_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        doThrow(illegalArgumentException)
                .when(suspiciousCardTransferRepository)
                .save(suspiciousCardTransfer);

        suspiciousCardTransferService.save(suspiciousCardTransfer);

        verify(suspiciousCardTransferRepository).save(suspiciousCardTransfer);
        verify(kafkaErrorLogger).handleIllegalArgumentException(illegalArgumentException, suspiciousCardTransfer);
        verifyNoMoreInteractions(kafkaErrorLogger);
    }

    @Test
    public void safeWithRuntimeException() {
        SuspiciousCardTransfer suspiciousCardTransfer = getSuspiciousCardTransfer(TEST_TRANSFER_ID, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);
        RuntimeException runtimeException = new RuntimeException(TEST_RUNTIME_EXCEPTION_MESSAGE);
        doThrow(runtimeException)
                .when(suspiciousCardTransferRepository)
                .save(suspiciousCardTransfer);

        suspiciousCardTransferService.save(suspiciousCardTransfer);

        verify(suspiciousCardTransferRepository).save(suspiciousCardTransfer);
        verify(kafkaErrorLogger).handleRuntimeException(runtimeException, suspiciousCardTransfer);
        verifyNoMoreInteractions(kafkaErrorLogger);
    }

    @Test
    void saveWithNullEntity() {
        SuspiciousCardTransfer entity = null;

        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(TEST_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        doThrow(illegalArgumentException).when(suspiciousCardTransferRepository).save(null);

        suspiciousCardTransferService.save(entity);

        verify(suspiciousCardTransferRepository).save(null);
        verify(kafkaErrorLogger).handleIllegalArgumentException(illegalArgumentException, null);
        verifyNoMoreInteractions(kafkaErrorLogger);
    }

    @Test
    void save_withEntityHavingNullFields() {
        SuspiciousCardTransfer entity = getSuspiciousCardTransfer(null, null, null);

        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(TEST_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        doThrow(illegalArgumentException).when(suspiciousCardTransferRepository).save(entity);

        suspiciousCardTransferService.save(entity);

        verify(suspiciousCardTransferRepository).save(entity);
        verify(kafkaErrorLogger).handleIllegalArgumentException(illegalArgumentException, entity);
        verifyNoMoreInteractions(kafkaErrorLogger);
    }

    @Test
    public void saveSuccessThroughService() {
        SuspiciousCardTransfer suspiciousCardTransfer = getSuspiciousCardTransfer(TEST_TRANSFER_ID, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);

        when(suspiciousCardTransferRepository.save(suspiciousCardTransfer))
                .thenReturn(suspiciousCardTransfer);

        suspiciousCardTransferService.save(suspiciousCardTransfer);

        verify(suspiciousCardTransferRepository).save(suspiciousCardTransfer);
        verifyNoInteractions(kafkaErrorLogger);
    }
}
