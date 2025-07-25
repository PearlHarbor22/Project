package com.bank.profile.service.impl;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.Audit;
import com.bank.profile.kafka.consumer.AuthConsumer;
import com.bank.profile.mapper.AuditMapper;
import com.bank.profile.enums.OperationType;
import com.bank.profile.repository.AuditRepository;
import com.bank.profile.service.AuditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditServiceImpl implements AuditService {

    private final ObjectMapper objectMapper;
    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;
    private final AuthConsumer authConsumer;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void audit(JoinPoint joinPoint, Object result) {
        if (result == null) {
            log.debug("Аудит пропущен: результат выполнения метода {} равен null.",
                    joinPoint.getSignature().getName());
            return;
        }

        Long currentUserId = authConsumer.getCurrentProfileId();
        if (currentUserId == null) {
            log.warn("Аудит пропущен: не удалось получить ID текущего пользователя (метод {}).",
                    joinPoint.getSignature().getName());
            return;
        }

        try {
            String methodName = joinPoint.getSignature().getName();
            OperationType operationType = OperationType.fromMethodName(methodName);
            boolean isUpdate = operationType == OperationType.UPDATE;

            String entityJson = objectMapper.writeValueAsString(result);

            AuditDto dto = AuditDto.builder()
                    .entityType(result.getClass().getSimpleName())
                    .operationType(operationType.name())
                    .createdBy(String.valueOf(currentUserId))
                    .modifiedBy(isUpdate ? String.valueOf(currentUserId) : null)
                    .createdAt(LocalDateTime.now())
                    .entityJson(entityJson)
                    .newEntityJson(isUpdate ? entityJson : null)
                    .build();

            Audit entity = auditMapper.toEntity(dto);
            auditRepository.save(entity);

            log.debug("Аудит успешно выполнен: метод {}, пользователь ID {}, тип сущности {}.",
                    methodName, currentUserId, result.getClass().getSimpleName());

        } catch (Exception ex) {
            log.error("Ошибка при выполнении аудита метода {}. Результат: {}",
                    joinPoint.getSignature().getName(), result, ex);
        }
    }
}
