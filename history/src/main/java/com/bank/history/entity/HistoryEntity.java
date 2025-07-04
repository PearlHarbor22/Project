package com.bank.history.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность, отображающая таблицу {@code history.history} в базе данных.
 * Содержит идентификаторы аудиторских записей из других микросервисов.
 */
@Entity
@Table(name = "history", schema = "history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryEntity {

    /**
     * Уникальный идентификатор записи истории.
     * Генерируется автоматически базой данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID аудита из микросервиса переводов (transfer).
     */
    @Column(name = "transfer_audit_id")
    private Long transferAuditId;

    /**
     * ID аудита из микросервиса профиля (profile).
     */
    @Column(name = "profile_audit_id")
    private Long profileAuditId;

    /**
     * ID аудита из микросервиса аккаунтов (account).
     */
    @Column(name = "account_audit_id")
    private Long accountAuditId;

    /**
     * ID аудита из микросервиса antifraud.
     */
    @Column(name = "anti_fraud_audit_id")
    private Long antiFraudAuditId;

    /**
     * ID аудита из микросервиса public bank info.
     */
    @Column(name = "public_bank_info_audit_id")
    private Long publicBankInfoAuditId;

    /**
     * ID аудита из микросервиса авторизации.
     */
    @Column(name = "authorization_audit_id")
    private Long authorizationAuditId;
}
