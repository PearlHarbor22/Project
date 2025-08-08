package com.bank.antifraud.service;

import com.bank.antifraud.config.KafkaErrorLogger;
import com.bank.antifraud.entity.SuspiciousAccountTransfer;
import com.bank.antifraud.repository.SuspiciousAccountTransferRepository;
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
import static com.bank.antifraud.util.ValidEntitiesCreator.getSuspiciousAccountTransfer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SuspiciousAccountTransferServiceTest {
    @Mock
    KafkaErrorLogger kafkaErrorLogger;
    @Mock
    SuspiciousAccountTransferRepository suspiciousAccountTransferRepository;
    @InjectMocks
    SuspiciousAccountTransferService suspiciousAccountTransferService;

    @Test
    public void saveSuccess() {
        SuspiciousAccountTransfer suspiciousAccountTransfer = getSuspiciousAccountTransfer(TEST_TRANSFER_ID, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);

        suspiciousAccountTransferService.save(suspiciousAccountTransfer);

        verify(suspiciousAccountTransferRepository).save(suspiciousAccountTransfer);
        verifyNoInteractions(kafkaErrorLogger);
    }

    @Test
    public void saveWithPersistenceException() {
        SuspiciousAccountTransfer suspiciousAccountTransfer = getSuspiciousAccountTransfer(TEST_TRANSFER_ID, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);
        PersistenceException persistenceException = new PersistenceException(TEST_PERSISTENT_EXCEPTION_MESSAGE);
        doThrow(persistenceException)
                .when(suspiciousAccountTransferRepository)
                .save(suspiciousAccountTransfer);

        suspiciousAccountTransferService.save(suspiciousAccountTransfer);

        verify(suspiciousAccountTransferRepository).save(suspiciousAccountTransfer);
        verify(kafkaErrorLogger).handlePersistenceException(persistenceException, suspiciousAccountTransfer);
        verifyNoMoreInteractions(kafkaErrorLogger);
    }

    @Test
    public void saveWithIllegalArgumentException() {
        SuspiciousAccountTransfer suspiciousAccountTransfer = getSuspiciousAccountTransfer(TEST_TRANSFER_ID, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(TEST_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        doThrow(illegalArgumentException)
                .when(suspiciousAccountTransferRepository)
                .save(suspiciousAccountTransfer);

        suspiciousAccountTransferService.save(suspiciousAccountTransfer);

        verify(suspiciousAccountTransferRepository).save(suspiciousAccountTransfer);
        verify(kafkaErrorLogger).handleIllegalArgumentException(illegalArgumentException, suspiciousAccountTransfer);
        verifyNoMoreInteractions(kafkaErrorLogger);
    }

    @Test
    public void saveWithRuntimeException() {
        SuspiciousAccountTransfer suspiciousAccountTransfer = getSuspiciousAccountTransfer(TEST_TRANSFER_ID, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);
        RuntimeException runtimeException = new RuntimeException(TEST_RUNTIME_EXCEPTION_MESSAGE);
        doThrow(runtimeException)
                .when(suspiciousAccountTransferRepository)
                .save(suspiciousAccountTransfer);

        suspiciousAccountTransferService.save(suspiciousAccountTransfer);

        verify(suspiciousAccountTransferRepository).save(suspiciousAccountTransfer);
        verify(kafkaErrorLogger).handleRuntimeException(runtimeException, suspiciousAccountTransfer);
        verifyNoMoreInteractions(kafkaErrorLogger);
    }

    @Test
    void saveWithNullEntity() {
        SuspiciousAccountTransfer entity = null;

        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(TEST_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        doThrow(illegalArgumentException).when(suspiciousAccountTransferRepository).save(null);

        suspiciousAccountTransferService.save(entity);

        verify(suspiciousAccountTransferRepository).save(null);
        verify(kafkaErrorLogger).handleIllegalArgumentException(illegalArgumentException, null);
        verifyNoMoreInteractions(kafkaErrorLogger);
    }

    @Test
    void save_withEntityHavingNullFields() {
        SuspiciousAccountTransfer entity = getSuspiciousAccountTransfer(null, null, null);

        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(TEST_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        doThrow(illegalArgumentException).when(suspiciousAccountTransferRepository).save(entity);

        suspiciousAccountTransferService.save(entity);

        verify(suspiciousAccountTransferRepository).save(entity);
        verify(kafkaErrorLogger).handleIllegalArgumentException(illegalArgumentException, entity);
        verifyNoMoreInteractions(kafkaErrorLogger);
    }

    @Test
    public void saveSuccessThroughService() {
        SuspiciousAccountTransfer suspiciousAccountTransfer = getSuspiciousAccountTransfer(TEST_TRANSFER_ID, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);

        when(suspiciousAccountTransferRepository.save(suspiciousAccountTransfer))
                .thenReturn(suspiciousAccountTransfer);

        suspiciousAccountTransferService.save(suspiciousAccountTransfer);

        verify(suspiciousAccountTransferRepository).save(suspiciousAccountTransfer);
        verifyNoInteractions(kafkaErrorLogger);

    }


}
