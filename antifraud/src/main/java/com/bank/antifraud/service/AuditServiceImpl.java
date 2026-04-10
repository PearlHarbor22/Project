package com.bank.antifraud.service;

import com.bank.antifraud.config.KafkaErrorLogger;
import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.kafka.consumer.AuthConsumer;
import com.bank.antifraud.kafka.producer.AuditProducer;
import com.bank.antifraud.mapper.AuditMapper;
import com.bank.antifraud.repository.AuditRepository;
import com.bank.antifraud.util.OperationType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;
    private final ObjectMapper objectMapper;
    private final AuthConsumer authConsumer;
    private final AuditProducer auditProducer;
    private final AuditMapper auditMapper;
    private final KafkaErrorLogger kafkaErrorLogger;


    @Override
    public void audit(JoinPoint joinPoint, Object result) {

        final String methodName = joinPoint.getSignature().getName();
        final String entityType = result.getClass().getSimpleName();
        OperationType operationType = OperationType.resolveByMethodName(methodName);
        try {
            try {
                if (authConsumer.getCurrentAuthId() == null) {
                    throw new NullPointerException("ID авторизации не найден");
                }
            } catch (NullPointerException e) {
                kafkaErrorLogger.handleNullPointerException(e, authConsumer.getCurrentAuthId());
            }
            Audit audit = Audit.builder()
                    .entityType(entityType)
                    .operationType(operationType.name())
                    .createdBy(authConsumer.getCurrentAuthId().toString())
                    .modifiedBy(operationType == OperationType.UPDATE ?
                            authConsumer.getCurrentAuthId().toString() : null)
                    .createdAt(LocalDateTime.now())
                    .newEntityJson(operationType == OperationType.UPDATE ?
                            objectMapper.writeValueAsString(result) : null)
                    .entityJson(objectMapper.writeValueAsString(result))
                    .build();

            auditRepository.save(audit);
            auditProducer.sendAudit(auditMapper.toAuditDto(audit));
        } catch (JsonProcessingException e) {
            kafkaErrorLogger.handleJsonProcessingException(e, result);
        } catch (NullPointerException e) {
            kafkaErrorLogger.handleNullPointerException(e, joinPoint);
        } catch (RuntimeException e) {
            kafkaErrorLogger.handleRuntimeException(e, joinPoint.getSignature().getName());
        }
    }
}
