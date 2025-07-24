package com.bank.publicinfo.Mappers;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entities.ATM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AtmMapper {
    ATM toEntity(AtmDto dto);

    AtmDto toDto(ATM atm);
}
