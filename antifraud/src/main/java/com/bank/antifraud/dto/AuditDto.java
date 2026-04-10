package com.bank.antifraud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditDto {

    private static final int ENTITY_TYPE_MAX_LENGTH = 40;
    private static final int DEFAULT_STRING_MAX_LENGTH = 255;

    private Long id;

    @NotBlank(message = "\"Entity type\" is mandatory")
    @Size(max = ENTITY_TYPE_MAX_LENGTH, message = "\"Entity type\" must be less than 40 characters")
    private String entityType;

    @NotBlank(message = "\"Operation type\" is mandatory")
    @Size(max = DEFAULT_STRING_MAX_LENGTH, message = "\"Operation type\" must be less than 255 characters")
    private String operationType;

    @NotBlank(message = "\"Created by\" is mandatory")
    @Size(max = DEFAULT_STRING_MAX_LENGTH, message = "\"Created by\" must be less than 255 characters")
    private String createdBy;

    @Size(max = DEFAULT_STRING_MAX_LENGTH, message = "\"Modified by\" must be less than 255 characters")
    private String modifiedBy;

    @NotNull(message = "\"Created at\" is mandatory")
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String newEntityJson;

    @NotBlank(message = "\"Entity JSON\" is mandatory")
    private String entityJson;
}
