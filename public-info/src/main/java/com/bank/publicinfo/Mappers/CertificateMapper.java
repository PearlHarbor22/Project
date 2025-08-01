package com.bank.publicinfo.Mappers;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entities.Certificate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CertificateMapper {
    Certificate toEntity(CertificateDto dto);

    CertificateDto toDto(Certificate certificate);
}
