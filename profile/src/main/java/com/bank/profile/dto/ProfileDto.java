package com.bank.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {

    private Long id;
    private Long phoneNumber;
    private String email;
    private String nameOnCard;
    private Long inn;
    private Long snils;
    private Long passportId;
    private Long actualRegistrationId;
}
