package com.bank.publicinfo.Mappers;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entities.Branch;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    Branch toEntity(BranchDto dto);

    BranchDto toDto(Branch branch);
}
