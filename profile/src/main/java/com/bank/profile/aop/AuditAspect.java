package com.bank.profile.aop;

import com.bank.profile.service.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {

    private final AuditService auditService;

    @Pointcut("@annotation(com.bank.profile.aop.Auditable)")
    public void auditedMethod() {
    }

    @AfterReturning(pointcut = "auditedMethod()", returning = "result")
    public void audit(JoinPoint joinPoint, Object result) {
        try {
            auditService.audit(joinPoint, result);
        } catch (Exception ex) {
            log.error("Ошибка при вызове аудита из аспекта для метода {}. Результат: {}",
                    joinPoint.getSignature().getName(), result, ex);
        }
    }
}
