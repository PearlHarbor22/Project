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
public class CardTransferDto implements TransferDto {

    @NotNull(message = "\"ID\" is mandatory")
    private Long id;

    @NotNull(message = "\"Account number\" is mandatory")
    private Long accountNumber;

    @NotNull(message = "\"Amount\" is mandatory")
    private Double amount;

    private String purpose;

    private Long accountDetailsId;
}
