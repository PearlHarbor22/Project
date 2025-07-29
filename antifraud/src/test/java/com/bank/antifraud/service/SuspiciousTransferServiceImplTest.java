package com.bank.antifraud.service;

import com.bank.antifraud.dto.AccountTransferDto;
import com.bank.antifraud.dto.CardTransferDto;
import com.bank.antifraud.dto.PhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransfer;
import com.bank.antifraud.entity.SuspiciousCardTransfer;
import com.bank.antifraud.entity.SuspiciousPhoneTransfer;
import com.bank.antifraud.kafka.producer.SuspiciousTransferProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bank.antifraud.util.Constants.AMOUNT_EQUALS_THRESHOLD;
import static com.bank.antifraud.util.Constants.TEST_BLOCKED_REASON;
import static com.bank.antifraud.util.Constants.TEST_ID;
import static com.bank.antifraud.util.Constants.TEST_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE;
import static com.bank.antifraud.util.Constants.TEST_RUNTIME_EXCEPTION_MESSAGE;
import static com.bank.antifraud.util.Constants.TEST_SUSPICIOUS_REASON;
import static com.bank.antifraud.util.ValidEntitiesCreator.createAccountTransferDto;
import static com.bank.antifraud.util.ValidEntitiesCreator.createCardTransferDto;
import static com.bank.antifraud.util.ValidEntitiesCreator.createPhoneTransferDto;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;


@ExtendWith(MockitoExtension.class)
class SuspiciousTransferServiceImplTest {
    @Mock
    SuspiciousAccountTransferService suspiciousAccountTransferService;
    @Mock
    SuspiciousCardTransferService suspiciousCardTransferService;
    @Mock
    SuspiciousPhoneTransferService suspiciousPhoneTransferService;
    @Mock
    SuspiciousTransferProducer suspiciousTransferProducer;
    @Mock
    com.bank.antifraud.config.KafkaErrorLogger kafkaErrorLogger;
    @InjectMocks
    SuspiciousTransferServiceImpl suspiciousTransferService;

    @Test
    void create_accountTransfer_success() {
        AccountTransferDto dto = createAccountTransferDto(TEST_ID, AMOUNT_EQUALS_THRESHOLD);

        SuspiciousAccountTransfer result = suspiciousTransferService
                .create(dto, true, true, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);

        verify(suspiciousAccountTransferService).save(any());
        verify(suspiciousTransferProducer).sendSuspiciousTransfer(any());
        assertNotNull(result);
    }

    @Test
    void create_cardTransfer_success() {
        CardTransferDto dto = createCardTransferDto(TEST_ID, AMOUNT_EQUALS_THRESHOLD);

        SuspiciousCardTransfer result = suspiciousTransferService
                .create(dto, true, true, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);

        verify(suspiciousCardTransferService).save(any());
        verify(suspiciousTransferProducer).sendSuspiciousTransfer(any());
        assertNotNull(result);
    }

    @Test
    void create_phoneTransfer_success() {
        PhoneTransferDto dto = createPhoneTransferDto(TEST_ID, AMOUNT_EQUALS_THRESHOLD);

        SuspiciousPhoneTransfer result = suspiciousTransferService
                .create(dto, true, true, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);

        verify(suspiciousPhoneTransferService).save(any());
        verify(suspiciousTransferProducer).sendSuspiciousTransfer(any());
        assertNotNull(result);
    }

    @Test
    void create_accountTransfer_withNullId_returnsNull() {
        AccountTransferDto dto = createAccountTransferDto(null, AMOUNT_EQUALS_THRESHOLD);
        SuspiciousAccountTransfer result = suspiciousTransferService
                .create(dto, true, true, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);

        verify(kafkaErrorLogger).handleEntityNotFoundException(any(), eq(dto));
        assertNull(result);
    }

    @Test
    void create_cardTransfer_withNullId_returnsNull() {
        CardTransferDto dto = createCardTransferDto(null, AMOUNT_EQUALS_THRESHOLD);

        SuspiciousCardTransfer result = suspiciousTransferService
                .create(dto, true, true, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);

        verify(kafkaErrorLogger).handleEntityNotFoundException(any(), eq(dto));
        assertNull(result);
    }

    @Test
    void create_phoneTransfer_withNullId_returnsNull() {
        PhoneTransferDto dto = createPhoneTransferDto(null, AMOUNT_EQUALS_THRESHOLD);

        SuspiciousPhoneTransfer result = suspiciousTransferService
                .create(dto, true, true, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);

        verify(kafkaErrorLogger).handleEntityNotFoundException(any(), eq(dto));
        assertNull(result);
    }

    @Test
    void create_accountTransfer_withIllegalArgumentException() {
        AccountTransferDto dto = createAccountTransferDto(TEST_ID, AMOUNT_EQUALS_THRESHOLD);
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(TEST_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        doThrow(illegalArgumentException).when(suspiciousAccountTransferService).save(any());

        SuspiciousAccountTransfer result = suspiciousTransferService
                .create(dto, true, true, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);

        verify(suspiciousAccountTransferService).save(any());
        verify(kafkaErrorLogger).handleIllegalArgumentException(illegalArgumentException, dto);
        verifyNoMoreInteractions(kafkaErrorLogger);
        assertNull(result);
    }

    @Test
    void create_cardTransfer_withIllegalArgumentException() {
        CardTransferDto dto = createCardTransferDto(TEST_ID, AMOUNT_EQUALS_THRESHOLD);
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(TEST_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        doThrow(illegalArgumentException).when(suspiciousCardTransferService).save(any());

        SuspiciousCardTransfer result = suspiciousTransferService
                .create(dto, true, true, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);

        verify(suspiciousCardTransferService).save(any());
        verify(kafkaErrorLogger).handleIllegalArgumentException(illegalArgumentException, dto);
        verifyNoMoreInteractions(kafkaErrorLogger);
        assertNull(result);
    }

    @Test
    void create_phoneTransfer_withIllegalArgumentException() {
        PhoneTransferDto dto = createPhoneTransferDto(TEST_ID, AMOUNT_EQUALS_THRESHOLD);
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(TEST_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        doThrow(illegalArgumentException).when(suspiciousPhoneTransferService).save(any());

        SuspiciousPhoneTransfer result = suspiciousTransferService
                .create(dto, true, true, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);

        verify(suspiciousPhoneTransferService).save(any());
        verify(kafkaErrorLogger).handleIllegalArgumentException(illegalArgumentException, dto);
        verifyNoMoreInteractions(kafkaErrorLogger);
        assertNull(result);
    }

    @Test
    void create_accountTransfer_withRuntimeException() {
        AccountTransferDto dto = createAccountTransferDto(TEST_ID, AMOUNT_EQUALS_THRESHOLD);
        RuntimeException runtimeException = new RuntimeException(TEST_RUNTIME_EXCEPTION_MESSAGE);
        doThrow(runtimeException).when(suspiciousAccountTransferService).save(any());

        SuspiciousAccountTransfer result = suspiciousTransferService
                .create(dto, true, true, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);

        verify(suspiciousAccountTransferService).save(any());
        verify(kafkaErrorLogger).handleRuntimeException(runtimeException, dto);
        verifyNoMoreInteractions(kafkaErrorLogger);
        assertNull(result);
    }

    @Test
    void create_cardTransfer_withRuntimeException() {
        CardTransferDto dto = createCardTransferDto(TEST_ID, AMOUNT_EQUALS_THRESHOLD);
        RuntimeException runtimeException = new RuntimeException(TEST_RUNTIME_EXCEPTION_MESSAGE);
        doThrow(runtimeException).when(suspiciousCardTransferService).save(any());

        SuspiciousCardTransfer result = suspiciousTransferService
                .create(dto, true, true, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);

        verify(suspiciousCardTransferService).save(any());
        verify(kafkaErrorLogger).handleRuntimeException(runtimeException, dto);
        verifyNoMoreInteractions(kafkaErrorLogger);
        assertNull(result);
    }

    @Test
    void create_phoneTransfer_withRuntimeException() {
        PhoneTransferDto dto = createPhoneTransferDto(TEST_ID, AMOUNT_EQUALS_THRESHOLD);
        RuntimeException runtimeException = new RuntimeException(TEST_RUNTIME_EXCEPTION_MESSAGE);
        doThrow(runtimeException).when(suspiciousPhoneTransferService).save(any());

        SuspiciousPhoneTransfer result = suspiciousTransferService
                .create(dto, true, true, TEST_BLOCKED_REASON, TEST_SUSPICIOUS_REASON);

        verify(suspiciousPhoneTransferService).save(any());
        verify(kafkaErrorLogger).handleRuntimeException(runtimeException, dto);
        verifyNoMoreInteractions(kafkaErrorLogger);
        assertNull(result);
    }
}
