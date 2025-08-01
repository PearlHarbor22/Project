package com.bank.history.util;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;

public class TestData {
    public static final Long ENTITY_ID_1 = 1L;
    public static final Long TRANSFER_AUDIT_ID = 2L;
    public static final Long PROFILE_AUDIT_ID = 3L;
    public static final Long ACCOUNT_AUDIT_ID = 4L;
    public static final Long ANTI_FRAUD_AUDIT_ID = 5L;
    public static final Long PUBLIC_BANK_INFO_AUDIT_ID = 6L;
    public static final Long AUTH_AUDIT_ID = 7L;

    public static final Long ENTITY_ID_10 = 10L;
    public static final Long TRANSFER_AUDIT_ID_2 = 20L;
    public static final Long PROFILE_AUDIT_ID_2 = 30L;
    public static final Long ACCOUNT_AUDIT_ID_2 = 40L;
    public static final Long ANTI_FRAUD_AUDIT_ID_2 = 50L;
    public static final Long PUBLIC_BANK_INFO_AUDIT_ID_2 = 60L;
    public static final Long AUTH_AUDIT_ID_2 = 70L;

    public static final Long SINGLE_ENTITY_ID = 100L;

    public static final HistoryDto TEST_DTO = HistoryDto.builder()
            .id(1L)
            .transferAuditId(2L)
            .profileAuditId(3L)
            .accountAuditId(4L)
            .antiFraudAuditId(5L)
            .publicBankInfoAuditId(6L)
            .authorizationAuditId(7L)
            .build();

    public static final HistoryEntity TEST_ENTITY = HistoryEntity.builder()
            .id(1L)
            .transferAuditId(2L)
            .profileAuditId(3L)
            .accountAuditId(4L)
            .antiFraudAuditId(5L)
            .publicBankInfoAuditId(6L)
            .authorizationAuditId(7L)
            .build();
    public static final String NOT_FOUND_MESSAGE = "Сущность не найдена";
    public static final String VALIDATION_ERROR_MESSAGE = "Ошибка валидации";
    public static final String PROCESSING_ERROR_MESSAGE = "Неожиданная ошибка сервиса";
    public static final String PAYLOAD_DATA = "payload_data";
    public static final int PAYLOAD_NUMBER = 42;
}
