package com.bank.publicinfo.services;

import com.bank.publicinfo.dto.BranchDto;
import java.util.List;

public interface BranchService {

    BranchDto create(BranchDto dto);

    BranchDto update(Long id, BranchDto dto);

    void delete(Long id);

    BranchDto getById(Long id);

    List<BranchDto> getAll();
}
