package com.bank.antifraud.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthDto {

    @NotNull(message = "\"ID\" is mandatory")
    private Long id;

    private String role;

    private String password;
}
