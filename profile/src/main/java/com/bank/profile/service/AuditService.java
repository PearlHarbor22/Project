package com.bank.profile.service;

import org.aspectj.lang.JoinPoint;

public interface AuditService {
    void audit(JoinPoint joinPoint, Object result);
}
