package com.bank.publicinfo.aop;

import com.bank.publicinfo.services.AuditService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {
    private final AuditService auditService;

    @Autowired
    public AuditAspect(@Lazy AuditService auditService) {this.auditService = auditService;}

    @Pointcut("within(@com.bank.publicinfo.services.AuditServiceImpl *)")
    public void auditedClass() {
    }
    @Pointcut("execution(* create*(..)) || execution(* update*(..))")
    public void auditableMethods() {
    }
    @Pointcut("auditedClass() && auditableMethods()")
    public void auditPointcut() {
    }
    @AfterReturning(pointcut = "auditPointcut()", returning = "result")
    public void afterAudit(JoinPoint joinPoint, Object result) {
        auditService.audit(joinPoint, result);
    }
}
