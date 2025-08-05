package com.bank.profile.mapper;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.Registration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {
    RegistrationDto toDto(Registration entity);

    Registration toEntity(RegistrationDto dto);
}
