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
public class SuspiciousPhoneTransferDto implements SuspiciousTransferDto {

    @NotNull(message = "\"ID\" is mandatory")
    private Long id;

    @NotNull(message = "\"Phone transfer ID\" is mandatory")
    private Long phoneTransferId;

    private Boolean isBlocked;

    private Boolean isSuspicious;

    private String blockedReason;

    private String suspiciousReason;
}
