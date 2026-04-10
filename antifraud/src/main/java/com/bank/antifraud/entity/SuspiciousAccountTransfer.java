package com.bank.antifraud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "suspicious_account_transfers")
public class SuspiciousAccountTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "\"Account transfer ID\" is mandatory")
    @Column(name = "account_transfer_id", nullable = false, unique = true)
    private Long accountTransferId;

    @NotNull(message = "\"Is blocked\" flag is mandatory")
    @Column(name = "is_blocked", nullable = false)
    private Boolean isBlocked;

    @NotNull(message = "\"Is suspicious\" flag is mandatory")
    @Column(name = "is_suspicious", nullable = false)
    private Boolean isSuspicious;

    @Column(name = "blocked_reason", nullable = true)
    private String blockedReason;

    @NotNull(message = "\"Suspicious reason\" is mandatory")
    @Column(name = "suspicious_reason", nullable = false)
    private String suspiciousReason;
}
