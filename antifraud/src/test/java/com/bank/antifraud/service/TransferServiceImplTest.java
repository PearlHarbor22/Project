package com.bank.antifraud.service;

import com.bank.antifraud.dto.AccountTransferDto;
import com.bank.antifraud.dto.CardTransferDto;
import com.bank.antifraud.dto.PhoneTransferDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bank.antifraud.util.Constants.AMOUNT_ABOVE_THRESHOLD;
import static com.bank.antifraud.util.Constants.AMOUNT_EQUALS_THRESHOLD;
import static com.bank.antifraud.util.Constants.SUSPICIOUS_THRESHOLD;
import static com.bank.antifraud.util.Constants.TEST_ID;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.isNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {


    @Mock
    SuspiciousTransferService suspiciousTransferService;
    @InjectMocks
    TransferServiceImpl transferService;

    @BeforeEach
    void setUp() throws Exception {
        java.lang.reflect.Field field = TransferServiceImpl.class.getDeclaredField("suspiciousThreshold");
        field.setAccessible(true);
        field.set(transferService, SUSPICIOUS_THRESHOLD);
    }

    @Test
    void process_accountTransferDto_aboveThreshold() {
        AccountTransferDto dto = AccountTransferDto.builder().id(TEST_ID).amount(AMOUNT_ABOVE_THRESHOLD).build();
        transferService.process(dto);
        verify(suspiciousTransferService).create(eq(dto), eq(true), eq(true), any(), any());
    }

    @Test
    void process_accountTransferDto_equalThreshold() {
        AccountTransferDto dto = AccountTransferDto.builder().id(TEST_ID).amount(AMOUNT_EQUALS_THRESHOLD).build();
        transferService.process(dto);
        verify(suspiciousTransferService).create(eq(dto), eq(false), eq(true), isNull(), any());
    }

    @Test
    void process_cardTransferDto_aboveThreshold() {
        CardTransferDto dto = CardTransferDto.builder().id(TEST_ID).amount(AMOUNT_ABOVE_THRESHOLD).build();
        transferService.process(dto);
        verify(suspiciousTransferService).create(eq(dto), eq(true), eq(true), any(), any());
    }

    @Test
    void process_cardTransferDto_equalThreshold() {
        CardTransferDto dto = CardTransferDto.builder().id(TEST_ID).amount(AMOUNT_EQUALS_THRESHOLD).build();
        transferService.process(dto);
        verify(suspiciousTransferService).create(eq(dto), eq(false), eq(true), isNull(), any());
    }

    @Test
    void process_phoneTransferDto_aboveThreshold() {
        PhoneTransferDto dto = PhoneTransferDto.builder().id(TEST_ID).amount(AMOUNT_ABOVE_THRESHOLD).build();
        transferService.process(dto);
        verify(suspiciousTransferService).create(eq(dto), eq(true), eq(true), any(), any());
    }

    @Test
    void process_phoneTransferDto_equalThreshold() {
        PhoneTransferDto dto = PhoneTransferDto.builder().id(TEST_ID).amount(AMOUNT_EQUALS_THRESHOLD).build();
        transferService.process(dto);
        verify(suspiciousTransferService).create(eq(dto), eq(false), eq(true), isNull(), any());
    }
}
