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
public class PhoneTransferDto implements TransferDto {

    @NotNull(message = "\"ID\" is mandatory")
    private Long id;

    @NotNull(message = "\"Phone number\" is mandatory")
    private Long phoneNumber;

    @NotNull(message = "\"Amount\" is mandatory")
    private Double amount;

    private String purpose;

    private Long accountDetailsId;
}
