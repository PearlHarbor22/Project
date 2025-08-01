package com.bank.history.kafka;

import com.bank.history.dto.HistoryDto;
import com.bank.history.handler.KafkaErrorLogger;
import com.bank.history.service.HistoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.bank.history.util.TestData.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

public class HistoryKafkaListenerTest {

    public static final String ERROR_NOT_FOUND = "not found";
    public static final String ERROR_INVALID = "invalid";
    public static final String ERROR_FAIL = "fail";
    public static final String ERROR_UNEXPECTED = "unexpected";
    private HistoryService historyService;
    private KafkaErrorLogger exceptionHandler;
    private HistoryKafkaListener listener;

    @BeforeEach
    void setUp() {
        historyService = mock(HistoryService.class);
        exceptionHandler = mock(KafkaErrorLogger.class);
        listener = new HistoryKafkaListener(historyService, exceptionHandler);
    }

    @Test
    void transfer_save() {
        HistoryDto dto = HistoryDto.builder().id(ENTITY_ID_1).build();

        listener.listenTransfer(dto);

        verify(historyService).save(dto);
        verify(exceptionHandler, never()).handleEntityNotFoundException(any(), any());
        verify(exceptionHandler, never()).handleValidationException(any(), any());
        verify(exceptionHandler, never()).handleProcessingError(any(), any());
    }

    @Test
    void profile_save() {
        HistoryDto dto = HistoryDto.builder().id(TRANSFER_AUDIT_ID).build();
        listener.listenProfile(dto);
        verify(historyService).save(dto);
    }

    @Test
    void account_save() {
        HistoryDto dto = HistoryDto.builder().id(PROFILE_AUDIT_ID).build();
        listener.listenAccount(dto);
        verify(historyService).save(dto);
    }

    @Test
    void antiFraud_save() {
        HistoryDto dto = HistoryDto.builder().id(ACCOUNT_AUDIT_ID).build();
        listener.listenAntiFraud(dto);
        verify(historyService).save(dto);
    }

    @Test
    void publicBankInfo_save() {
        HistoryDto dto = HistoryDto.builder().id(ANTI_FRAUD_AUDIT_ID).build();
        listener.listenPublicBankInfo(dto);
        verify(historyService).save(dto);
    }

    @Test
    void authorization_save() {
        HistoryDto dto = HistoryDto.builder().id(PUBLIC_BANK_INFO_AUDIT_ID).build();
        listener.listenAuthorization(dto);
        verify(historyService).save(dto);
    }

    @Test
    void transfer_entityNotFound() {
        HistoryDto dto = HistoryDto.builder().id(ENTITY_ID_10).build();
        doThrow(new EntityNotFoundException(ERROR_NOT_FOUND)).when(historyService).save(dto);

        listener.listenTransfer(dto);

        verify(exceptionHandler).handleEntityNotFoundException(any(EntityNotFoundException.class), eq(dto));
    }

    @Test
    void profile_validationError() {
        HistoryDto dto = HistoryDto.builder().id(TRANSFER_AUDIT_ID_2).build();
        doThrow(new ValidationException(ERROR_INVALID)).when(historyService).save(dto);

        listener.listenProfile(dto);

        verify(exceptionHandler).handleValidationException(any(ValidationException.class), eq(dto));
    }

    @Test
    void account_processingError() {
        HistoryDto dto = HistoryDto.builder().id(PROFILE_AUDIT_ID_2).build();
        doThrow(new RuntimeException(ERROR_FAIL)).when(historyService).save(dto);

        listener.listenAccount(dto);

        verify(exceptionHandler).handleProcessingError(any(RuntimeException.class), eq(dto));
    }

    @Test
    void transfer_processingError() {
        HistoryDto dto = HistoryDto.builder().id(SINGLE_ENTITY_ID).build();
        doThrow(new RuntimeException(ERROR_UNEXPECTED)).when(historyService).save(dto);

        listener.listenTransfer(dto);

        verify(exceptionHandler).handleProcessingError(any(RuntimeException.class), eq(dto));
    }

    @Test
    void antiFraud_entityNotFound() {
        HistoryDto dto = HistoryDto.builder().id(TRANSFER_AUDIT_ID_2).build();
        doThrow(new EntityNotFoundException(ERROR_NOT_FOUND)).when(historyService).save(dto);

        listener.listenAntiFraud(dto);

        verify(exceptionHandler).handleEntityNotFoundException(any(EntityNotFoundException.class), eq(dto));
    }

    @Test
    void publicBankInfo_validationError() {
        HistoryDto dto = HistoryDto.builder().id(PROFILE_AUDIT_ID_2).build();
        doThrow(new ValidationException(ERROR_INVALID)).when(historyService).save(dto);

        listener.listenPublicBankInfo(dto);

        verify(exceptionHandler).handleValidationException(any(ValidationException.class), eq(dto));
    }

    @Test
    void authorization_processingError() {
        HistoryDto dto = HistoryDto.builder().id(ACCOUNT_AUDIT_ID_2).build();
        doThrow(new RuntimeException(ERROR_FAIL)).when(historyService).save(dto);

        listener.listenAuthorization(dto);

        verify(exceptionHandler).handleProcessingError(any(RuntimeException.class), eq(dto));
    }
}