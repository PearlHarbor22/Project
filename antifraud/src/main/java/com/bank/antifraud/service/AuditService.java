package com.bank.antifraud.service;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Service;

@Service
public interface AuditService {

    void audit(JoinPoint joinPoint, Object result);

}
