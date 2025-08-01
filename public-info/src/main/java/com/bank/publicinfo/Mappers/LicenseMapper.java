package com.bank.publicinfo.Mappers;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entities.License;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface LicenseMapper {
    License toEntity(LicenseDto dto);

    LicenseDto toDto(License license);
}
