package com.bank.profile.mapper;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActualRegistrationMapper {
    ActualRegistrationDto toDto(ActualRegistration entity);

    ActualRegistration toEntity(ActualRegistrationDto dto);
}
