package com.bank.profile.mapper;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsId;
import com.bank.profile.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AccountDetailsIdMapper {

    @Mapping(target = "profile", source = "profileId", qualifiedByName = "toProfile")
    AccountDetailsId toEntity(AccountDetailsIdDto dto);

    @Mapping(source = "profile.id", target = "profileId")
    AccountDetailsIdDto toDto(AccountDetailsId entity);

    @Named("toProfile")
    default Profile toProfile(Long id) {
        return id == null ? null : Profile.builder().id(id).build();
    }
}
