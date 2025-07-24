package com.bank.publicinfo.services;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Service;

@Service
public interface AuditService {
    /**
     * Выполняет аудит переданных данных после успешного выполнения метода create/update.
     *
     * @param joinPoint — контекст вызова (название метода, параметры и т.д.)
     * @param result — результат метода, который надо зааудитить
     */
    void audit(JoinPoint joinPoint, Object result);
}
