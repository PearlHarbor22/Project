package com.bank.publicinfo.services;

import com.bank.publicinfo.Mappers.AuditMapper;
import com.bank.publicinfo.config.KafkaErrorLogger;
import com.bank.publicinfo.entities.Audit;
import com.bank.publicinfo.kafkaProducer.AuditProducer;
import com.bank.publicinfo.repositories.AuditRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;
    private final ObjectMapper objectMapper;
    private final KafkaErrorLogger kafkaErrorLogger;
    private final AuditProducer auditProducer;
    private final AuditMapper auditMapper;

    @Override
    public void audit(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String entityType = result.getClass().getSimpleName().replace("Dto", "");

        try {
            String entityJson = objectMapper.writeValueAsString(result);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = (authentication != null && authentication.isAuthenticated())
                    ? authentication.getName()
                    : "anonymous";

            Audit audit = Audit.builder()
                    .entityType(entityType)
                    .operationType(getOperationType(methodName))
                    .createdBy(username)
                    .modifiedBy(methodName.contains("update") ? username : null)
                    .createdAt(LocalDateTime.now())
                    .modifiedAt(methodName.contains("update") ? LocalDateTime.now() : null)
                    .entityJson(entityJson)
                    .newEntityJson(methodName.contains("update") ? entityJson : null)
                    .build();

            auditRepository.save(audit);
            auditProducer.sendAudit(auditMapper.toAuditDto(audit));
        } catch (JsonProcessingException e) {
            kafkaErrorLogger.handleError(e, result, "Ошибка сериализации при аудите");
        } catch (RuntimeException e) {
            kafkaErrorLogger.handleError(e, methodName, "Ошибка выполнения метода аудита" + methodName);
        }
    }

    private String getOperationType(String methodName) {
        if (methodName.startsWith("create")) return "CREATE";
        if (methodName.startsWith("update")) return "UPDATE";
        return "UNKNOWN";
    }
}
