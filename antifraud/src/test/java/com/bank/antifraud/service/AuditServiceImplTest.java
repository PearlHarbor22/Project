package com.bank.antifraud.service;

import com.bank.antifraud.config.KafkaErrorLogger;
import com.bank.antifraud.kafka.consumer.AuthConsumer;
import com.bank.antifraud.kafka.producer.AuditProducer;
import com.bank.antifraud.mapper.AuditMapper;
import com.bank.antifraud.repository.AuditRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bank.antifraud.util.Constants.CREATE_METHOD_NAME;
import static com.bank.antifraud.util.Constants.ERROR_MESSAGE;
import static com.bank.antifraud.util.Constants.TEST_ID;
import static com.bank.antifraud.util.Constants.TEST_JSON_CONTENT;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditServiceImplTest {

    @Mock
    private AuditRepository auditRepository;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private AuthConsumer authConsumer;
    @Mock
    private AuditProducer auditProducer;
    @Mock
    private AuditMapper auditMapper;
    @Mock
    private KafkaErrorLogger kafkaErrorLogger;
    @Mock
    private JoinPoint joinPoint;
    @Mock
    Signature signature;

    @InjectMocks
    private AuditServiceImpl auditService;

    @Test
    void auditSuccess() throws Exception {
        Object result = new Object();
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn(CREATE_METHOD_NAME);
        when(authConsumer.getCurrentAuthId()).thenReturn(TEST_ID);
        when(objectMapper.writeValueAsString(result)).thenReturn(TEST_JSON_CONTENT);
        when(auditMapper.toAuditDto(any())).thenReturn(null);

        auditService.audit(joinPoint, result);

        verify(auditRepository).save(any());
        verify(auditProducer).sendAudit(any());
    }

    @Test
    void auditNullAuthIdLogsError() {
        Object result = new Object();
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn(CREATE_METHOD_NAME);
        when(authConsumer.getCurrentAuthId()).thenReturn(null);
        auditService.audit(joinPoint, result);
        verify(kafkaErrorLogger).handleNullPointerException(any(), isNull());
        verifyNoInteractions(auditRepository, auditProducer);
    }

    @Test
    void auditJsonProcessingException() throws JsonProcessingException {
        Object result = new Object();
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn(CREATE_METHOD_NAME);
        when(authConsumer.getCurrentAuthId()).thenReturn(TEST_ID);
        when(objectMapper.writeValueAsString(result))
                .thenThrow(new JsonProcessingException(ERROR_MESSAGE) {
                });

        auditService.audit(joinPoint, result);
        verify(kafkaErrorLogger).handleJsonProcessingException(any(), eq(result));
        verifyNoInteractions(auditRepository, auditProducer);

    }

    @Test
    void auditRuntimeException() throws JsonProcessingException {
        Object result = new Object();
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn(CREATE_METHOD_NAME);
        when(authConsumer.getCurrentAuthId()).thenReturn(TEST_ID);
        when(objectMapper.writeValueAsString(result))
                .thenThrow(new RuntimeException(ERROR_MESSAGE) {
                });

        auditService.audit(joinPoint, result);
        verify(kafkaErrorLogger).handleRuntimeException(any(), eq(CREATE_METHOD_NAME));
        verifyNoInteractions(auditRepository, auditProducer);
    }
}
