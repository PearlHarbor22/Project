package com.bank.publicinfo.Mappers;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entities.BankDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BankDetailsMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "bik", source = "bik")
    @Mapping(target = "inn", source = "inn")
    @Mapping(target = "kpp", source = "kpp")
    @Mapping(target = "corAccount", source = "corAccount")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "jointStockCompany", source = "jointStockCompany")
    @Mapping(target = "name", source = "name")
    BankDetailsDto toDto(BankDetails entity);

    BankDetails toEntity(BankDetailsDto dto);
}
