package com.bank.history.kafka;

import com.bank.history.dto.HistoryDto;
import com.bank.history.handler.KafkaErrorLogger;
import com.bank.history.service.HistoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

public class HistoryKafkaListenerTest {

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
    void listenTransfer_CallsServiceSave() {
        HistoryDto dto = HistoryDto.builder().id(1L).build();

        listener.listenTransfer(dto);

        verify(historyService).save(dto);
        verify(exceptionHandler, never()).handleEntityNotFoundException(Mockito.any(), Mockito.any());
        verify(exceptionHandler, never()).handleValidationException(Mockito.any(), Mockito.any());
        verify(exceptionHandler, never()).handleProcessingError(Mockito.any(), Mockito.any());
    }

    @Test
    void listenProfile_CallsServiceSave() {
        HistoryDto dto = HistoryDto.builder().id(2L).build();
        listener.listenProfile(dto);
        verify(historyService).save(dto);
    }

    @Test
    void listenAccount_CallsServiceSave() {
        HistoryDto dto = HistoryDto.builder().id(3L).build();
        listener.listenAccount(dto);
        verify(historyService).save(dto);
    }

    @Test
    void listenAntiFraud_CallsServiceSave() {
        HistoryDto dto = HistoryDto.builder().id(4L).build();
        listener.listenAntiFraud(dto);
        verify(historyService).save(dto);
    }

    @Test
    void listenPublicBankInfo_CallsServiceSave() {
        HistoryDto dto = HistoryDto.builder().id(5L).build();
        listener.listenPublicBankInfo(dto);
        verify(historyService).save(dto);
    }

    @Test
    void listenAuthorization_CallsServiceSave() {
        HistoryDto dto = HistoryDto.builder().id(6L).build();
        listener.listenAuthorization(dto);
        verify(historyService).save(dto);
    }

    @Test
    void listenTransfer_EntityNotFoundException_Handled() {
        HistoryDto dto = HistoryDto.builder().id(10L).build();

        doThrow(new EntityNotFoundException("not found")).when(historyService).save(dto);

        listener.listenTransfer(dto);

        verify(exceptionHandler).handleEntityNotFoundException(Mockito.any(EntityNotFoundException.class), Mockito.eq(dto));
    }

    @Test
    void listenProfile_ValidationException_Handled() {
        HistoryDto dto = HistoryDto.builder().id(11L).build();
        doThrow(new ValidationException("invalid")).when(historyService).save(dto);

        listener.listenProfile(dto);

        verify(exceptionHandler).handleValidationException(Mockito.any(ValidationException.class), Mockito.eq(dto));
    }

    @Test
    void listenAccount_OtherException_Handled() {
        HistoryDto dto = HistoryDto.builder().id(12L).build();
        doThrow(new RuntimeException("fail")).when(historyService).save(dto);

        listener.listenAccount(dto);

        verify(exceptionHandler).handleProcessingError(Mockito.any(RuntimeException.class), Mockito.eq(dto));
    }
    @Test
    void listenTransfer_GenericException_Handled() {
        HistoryDto dto = HistoryDto.builder().id(100L).build();
        doThrow(new RuntimeException("unexpected")).when(historyService).save(dto);

        listener.listenTransfer(dto);

        verify(exceptionHandler).handleProcessingError(any(RuntimeException.class), eq(dto));
    }

    @Test
    void listenAntiFraud_EntityNotFoundException_Handled() {
        HistoryDto dto = HistoryDto.builder().id(101L).build();
        doThrow(new EntityNotFoundException("not found")).when(historyService).save(dto);

        listener.listenAntiFraud(dto);

        verify(exceptionHandler).handleEntityNotFoundException(any(EntityNotFoundException.class), eq(dto));
    }

    @Test
    void listenPublicBankInfo_ValidationException_Handled() {
        HistoryDto dto = HistoryDto.builder().id(102L).build();
        doThrow(new ValidationException("invalid")).when(historyService).save(dto);

        listener.listenPublicBankInfo(dto);

        verify(exceptionHandler).handleValidationException(any(ValidationException.class), eq(dto));
    }

    @Test
    void listenAuthorization_GenericException_Handled() {
        HistoryDto dto = HistoryDto.builder().id(103L).build();
        doThrow(new RuntimeException("fail")).when(historyService).save(dto);

        listener.listenAuthorization(dto);

        verify(exceptionHandler).handleProcessingError(any(RuntimeException.class), eq(dto));
    }
}