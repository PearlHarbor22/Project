package com.bank.profile.service.impl;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.kafka.consumer.AuthConsumer;
import com.bank.profile.mapper.AuditMapper;
import com.bank.profile.repository.AuditRepository;
import com.bank.profile.service.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.bank.profile.enums.OperationType.fromMethodName;
import static com.bank.profile.enums.OperationType.UPDATE;
import static com.bank.profile.enums.OperationType.CREATE;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditServiceImpl implements AuditService {

    private final ObjectMapper objectMapper;
    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;
    private final AuthConsumer authConsumer;

    @Override
    @Transactional
    public void audit(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        var operationType = fromMethodName(methodName);

        if (operationType != CREATE && operationType != UPDATE) {
            log.debug("Аудит пропущен: операция {} не подлежит аудиту", operationType);
            return;
        }

        if (result == null) {
            log.debug("Аудит пропущен: результат метода {} равен null.", methodName);
            return;
        }

        Long currentUserId = authConsumer.getCurrentProfileId();
        if (currentUserId == null) {
            log.warn("Аудит пропущен: не удалось получить ID текущего пользователя (метод {}).", methodName);
            return;
        }

        String entityJson;
        try {
            entityJson = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            log.error("Ошибка сериализации сущности для аудита: {}", result, e);
            return;
        }

        boolean isUpdate = operationType == UPDATE;

        AuditDto dto = AuditDto.builder()
                .entityType(result.getClass().getSimpleName())
                .operationType(operationType.name())
                .createdBy(String.valueOf(currentUserId))
                .modifiedBy(isUpdate ? String.valueOf(currentUserId) : null)
                .createdAt(LocalDateTime.now())
                .entityJson(entityJson)
                .newEntityJson(isUpdate ? entityJson : null)
                .build();

        auditRepository.save(auditMapper.toEntity(dto));

        log.info("Аудит выполнен: метод {}, пользователь ID {}, сущность {}.",
                methodName, currentUserId, result.getClass().getSimpleName());
    }
}
