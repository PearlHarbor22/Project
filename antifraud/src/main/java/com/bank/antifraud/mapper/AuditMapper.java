package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.entity.Audit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditMapper {
    AuditDto toAuditDto(Audit audit);
}
