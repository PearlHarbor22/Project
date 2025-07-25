package com.bank.profile.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "passport", schema = "profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer series;

    @Column(nullable = false)
    private Long number;

    @Column(name = "last_name", length = 255, nullable = false)
    private String lastName;

    @Column(name = "first_name", length = 255, nullable = false)
    private String firstName;

    @Column(name = "middle_name", length = 255)
    private String middleName;

    @Column(length = 3, nullable = false)
    private String gender;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "birth_place", length = 480, nullable = false)
    private String birthPlace;

    @Column(name = "issued_by", columnDefinition = "text", nullable = false)
    private String issuedBy;

    @Column(name = "date_of_issue", nullable = false)
    private LocalDate dateOfIssue;

    @Column(name = "division_code", nullable = false)
    private String divisionCode;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @OneToOne
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;
}
