package com.bank.antifraud.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class SuspiciousAccountTransferDto implements SuspiciousTransferDto {

    @NotNull(message = "\"ID\" is mandatory")
    private Long id;

    @NotNull(message = "\"Account transferID\" is mandatory")
    private Long accountTransferId;

    private Boolean isBlocked;

    private Boolean isSuspicious;

    private String blockedReason;

    private String suspiciousReason;

}
