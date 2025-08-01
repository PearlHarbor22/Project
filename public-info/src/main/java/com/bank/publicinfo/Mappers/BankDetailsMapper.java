package com.bank.publicinfo.Mappers;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entities.BankDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankDetailsMapper {

    BankDetailsDto toDto(BankDetails entity);

    BankDetails toEntity(BankDetailsDto dto);
}
