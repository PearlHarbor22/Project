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
public class AccountTransferDto implements TransferDto {

    @NotNull(message = "\"Operation ID\" is mandatory")
    private Long id;

    private Long accountNumber;

    @NotNull(message = "\"Amount\" is mandatory")
    private Double amount;

    private String purpose;

    private Long accountDetailsId;
}
