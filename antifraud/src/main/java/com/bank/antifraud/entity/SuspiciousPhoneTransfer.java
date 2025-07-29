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
@Entity
@Table(name = "suspicious_phone_transfers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuspiciousPhoneTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "\"Phone transfer ID\" is mandatory")
    @Column(name = "phone_transfer_id", nullable = false, unique = true)
    private Long phoneTransferId;

    @NotNull(message = "\"Is blocked\" is mandatory")
    @Column(name = "is_blocked", nullable = false)
    private Boolean isBlocked;

    @NotNull(message = "\"Is suspicious\" is mandatory")
    @Column(name = "is_suspicious", nullable = false)
    private Boolean isSuspicious;

    @Column(name = "blocked_reason", nullable = true)
    private String blockedReason;

    @NotNull(message = "\"Suspicious reason\" is mandatory")
    @Column(name = "suspicious_reason", nullable = false)
    private String suspiciousReason;
}
