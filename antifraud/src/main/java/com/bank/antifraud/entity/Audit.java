package com.bank.antifraud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "audit")
public class Audit {

    private static final int ENTITY_TYPE_MAX_LENGTH = 40;
    private static final int DEFAULT_STRING_MAX_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "\"Entity type\" is mandatory")
    @Size(max = ENTITY_TYPE_MAX_LENGTH, message = "\"Entity type\" must be less than 40 characters")
    @Column(name = "entity_type", nullable = false)
    private String entityType;

    @NotBlank(message = "\"Operation type\" is mandatory")
    @Size(max = DEFAULT_STRING_MAX_LENGTH, message = "\"Operation type\" must be less than 255 characters")
    @Column(name = "operation_type", nullable = false)
    private String operationType;

    @NotBlank(message = "\"Created by\" is mandatory")
    @Size(max = DEFAULT_STRING_MAX_LENGTH, message = "\"Created by\" must be less than 255 characters")
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Size(max = DEFAULT_STRING_MAX_LENGTH, message = "\"Modified by\" must be less than 255 characters")
    @Column(name = "modified_by", nullable = true)
    private String modifiedBy;

    @NotNull(message = "\"Created at\" is mandatory")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = true)
    private LocalDateTime modifiedAt;

    @Column(name = "new_entity_json", nullable = true)
    private String newEntityJson;

    @NotBlank(message = "\"Entity JSON\" is mandatory")
    @Column(name = "entity_json", nullable = false)
    private String entityJson;

}
