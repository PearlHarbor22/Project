package com.bank.antifraud.aop;

import com.bank.antifraud.config.KafkaErrorLogger;
import com.bank.antifraud.exception.EmptyReturnedValueException;
import com.bank.antifraud.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {
    private final AuditService auditService;
    private final KafkaErrorLogger kafkaErrorLogger;

    @Pointcut("within(@com.bank.antifraud.aspect.Audited *)")
    public void auditedClass() {
    }

    @Pointcut("execution(* create*(..)) || execution(* update*(..))")
    public void auditedMethod() {
    }

    @Pointcut("auditedClass() && auditedMethod()")
    public void auditPointcut() {
    }

    @AfterReturning(pointcut = "auditPointcut()", returning = "result")
    public void audit(JoinPoint joinPoint, Object result) {

        if (result == null) {
            kafkaErrorLogger.handleEmptyReturnedValueException(new EmptyReturnedValueException(
                    "Метод, помеченный для аудита, вернул пустое значение или не вернул значение"), joinPoint);
            return;
        }
        auditService.audit(joinPoint, result);
    }
}
