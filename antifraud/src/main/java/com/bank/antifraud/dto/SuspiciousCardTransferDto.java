package com.bank.antifraud.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuspiciousCardTransferDto implements SuspiciousTransferDto {

    @NotNull(message = "\"ID\" is mandatory")
    private Long id;

    @NotNull(message = "\"Card transfer ID\" is mandatory")
    private Long cardTransferId;

    private Boolean isBlocked;

    private Boolean isSuspicious;

    private String blockedReason;

    private String suspiciousReason;
}
