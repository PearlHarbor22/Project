package com.bank.profile.mapper;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ActualRegistration;
import com.bank.profile.entity.Passport;
import com.bank.profile.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(target = "passport", expression = "java(toPassport(dto.getPassportId()))")
    @Mapping(target = "actualRegistration", expression = "java(toActualRegistration(dto.getActualRegistrationId()))")
    Profile toEntity(ProfileDto dto);

    @Mapping(source = "passport.id", target = "passportId")
    @Mapping(source = "actualRegistration.id", target = "actualRegistrationId")
    ProfileDto toDto(Profile entity);

    default Passport toPassport(Long id) {
        return id == null ? null : Passport.builder().id(id).build();
    }

    default ActualRegistration toActualRegistration(Long id) {
        return id == null ? null : ActualRegistration.builder().id(id).build();
    }
}
