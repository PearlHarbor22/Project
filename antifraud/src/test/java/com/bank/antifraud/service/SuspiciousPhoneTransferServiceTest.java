package com.bank.antifraud.service;

import com.bank.antifraud.config.KafkaErrorLogger;
import com.bank.antifraud.entity.SuspiciousPhoneTransfer;
import com.bank.antifraud.repository.SuspiciousPhoneTransferRepository;
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
import static com.bank.antifraud.util.ValidEntitiesCreator.getSuspiciousPhoneTransfer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SuspiciousPhoneTransferServiceTest {
    @Mock
    KafkaErrorLogger kafkaErrorLogger;
    @Mock
    SuspiciousPhoneTransferRepository suspiciousPhoneTransferRepository;
    @InjectMocks
    SuspiciousPhoneTransferService suspiciousPhoneTransferService;

    @Test
    public void saveSuccess() {
        SuspiciousPhoneTransfer suspiciousPhoneTransfer = getSuspiciousPhoneTransfer(TEST_TRANSFER_ID, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);

        suspiciousPhoneTransferRepository.save(suspiciousPhoneTransfer);

        verify(suspiciousPhoneTransferRepository).save(suspiciousPhoneTransfer);
        verifyNoInteractions(kafkaErrorLogger);
    }


    @Test
    public void safeWithPersistenceException() {
        SuspiciousPhoneTransfer suspiciousPhoneTransfer = getSuspiciousPhoneTransfer(TEST_TRANSFER_ID, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);
        PersistenceException persistenceException = new PersistenceException(TEST_PERSISTENT_EXCEPTION_MESSAGE);
        doThrow(persistenceException)
                .when(suspiciousPhoneTransferRepository)
                .save(suspiciousPhoneTransfer);

        suspiciousPhoneTransferService.save(suspiciousPhoneTransfer);

        verify(suspiciousPhoneTransferRepository).save(suspiciousPhoneTransfer);
        verify(kafkaErrorLogger).handlePersistenceException(persistenceException, suspiciousPhoneTransfer);
        verifyNoMoreInteractions(kafkaErrorLogger);
    }

    @Test
    public void safeWithIllegalArgumentException() {
        SuspiciousPhoneTransfer suspiciousPhoneTransfer = getSuspiciousPhoneTransfer(TEST_TRANSFER_ID, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(TEST_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        doThrow(illegalArgumentException)
                .when(suspiciousPhoneTransferRepository)
                .save(suspiciousPhoneTransfer);

        suspiciousPhoneTransferService.save(suspiciousPhoneTransfer);

        verify(suspiciousPhoneTransferRepository).save(suspiciousPhoneTransfer);
        verify(kafkaErrorLogger).handleIllegalArgumentException(illegalArgumentException, suspiciousPhoneTransfer);
        verifyNoMoreInteractions(kafkaErrorLogger);
    }

    @Test
    public void safeWithRuntimeException() {
        SuspiciousPhoneTransfer suspiciousPhoneTransfer = getSuspiciousPhoneTransfer(TEST_TRANSFER_ID, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);
        RuntimeException runtimeException = new RuntimeException(TEST_RUNTIME_EXCEPTION_MESSAGE);
        doThrow(runtimeException)
                .when(suspiciousPhoneTransferRepository)
                .save(suspiciousPhoneTransfer);

        suspiciousPhoneTransferService.save(suspiciousPhoneTransfer);

        verify(suspiciousPhoneTransferRepository).save(suspiciousPhoneTransfer);
        verify(kafkaErrorLogger).handleRuntimeException(runtimeException, suspiciousPhoneTransfer);
        verifyNoMoreInteractions(kafkaErrorLogger);
    }

    @Test
    void saveWithNullEntity() {
        SuspiciousPhoneTransfer entity = null;

        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(TEST_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        doThrow(illegalArgumentException).when(suspiciousPhoneTransferRepository).save(null);

        suspiciousPhoneTransferService.save(entity);

        verify(suspiciousPhoneTransferRepository).save(null);
        verify(kafkaErrorLogger).handleIllegalArgumentException(illegalArgumentException, null);
        verifyNoMoreInteractions(kafkaErrorLogger);
    }

    @Test
    void save_withEntityHavingNullFields() {
        SuspiciousPhoneTransfer entity = getSuspiciousPhoneTransfer(null, null, null);

        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(TEST_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        doThrow(illegalArgumentException).when(suspiciousPhoneTransferRepository).save(entity);

        suspiciousPhoneTransferService.save(entity);

        verify(suspiciousPhoneTransferRepository).save(entity);
        verify(kafkaErrorLogger).handleIllegalArgumentException(illegalArgumentException, entity);
        verifyNoMoreInteractions(kafkaErrorLogger);
    }

    @Test
    public void saveSuccessThroughService() {
        SuspiciousPhoneTransfer suspiciousPhoneTransfer = getSuspiciousPhoneTransfer(TEST_TRANSFER_ID, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);

        when(suspiciousPhoneTransferRepository.save(suspiciousPhoneTransfer))
                .thenReturn(suspiciousPhoneTransfer);

        suspiciousPhoneTransferService.save(suspiciousPhoneTransfer);

        verify(suspiciousPhoneTransferRepository).save(suspiciousPhoneTransfer);
        verifyNoInteractions(kafkaErrorLogger);
    }


}
