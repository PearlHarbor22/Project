package com.bank.profile.mapper;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.Passport;
import com.bank.profile.entity.Registration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PassportMapper {
    @Mapping(target = "registration", expression = "java(toRegistration(dto.getRegistrationId()))")
    Passport toEntity(PassportDto dto);

    @Mapping(source = "registration.id", target = "registrationId")
    PassportDto toDto(Passport entity);

    default Registration toRegistration(Long id) {
        return id == null ? null : Registration.builder().id(id).build();
    }
}
