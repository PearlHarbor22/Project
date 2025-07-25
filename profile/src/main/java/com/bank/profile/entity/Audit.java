package com.bank.profile.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "audit", schema = "profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Audit implements Serializable {

    private static final int ENTITY_TYPE_MAX_LENGTH = 40;
    private static final int DEFAULT_STRING_MAX_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = ENTITY_TYPE_MAX_LENGTH)
    @Column(name = "entity_type", nullable = false)
    private String entityType;

    @NotBlank
    @Size(max = DEFAULT_STRING_MAX_LENGTH)
    @Column(name = "operation_type", nullable = false)
    private String operationType;

    @NotBlank
    @Size(max = DEFAULT_STRING_MAX_LENGTH)
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Size(max = DEFAULT_STRING_MAX_LENGTH)
    @Column(name = "modified_by")
    private String modifiedBy;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "new_entity_json", columnDefinition = "text")
    private String newEntityJson;

    @NotBlank
    @Column(name = "entity_json", columnDefinition = "text", nullable = false)
    private String entityJson;
}
