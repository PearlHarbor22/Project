package com.bank.publicinfo.services;

import com.bank.publicinfo.dto.BranchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BranchService {

    BranchDto create(BranchDto dto);

    BranchDto update(Long id, BranchDto dto);

    void delete(Long id);

    BranchDto getById(Long id);

    Page<BranchDto> getAll(Pageable pageable);
}
