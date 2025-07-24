package com.bank.publicinfo.Mappers;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.entities.Audit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditMapper {
    AuditDto toAuditDto(Audit audit);

    Audit toAudit(AuditDto auditDto);
}
