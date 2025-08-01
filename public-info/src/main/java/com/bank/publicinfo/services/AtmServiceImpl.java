package com.bank.publicinfo.services;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entities.ATM;
import com.bank.publicinfo.entities.Branch;
import com.bank.publicinfo.Mappers.AtmMapper;
import com.bank.publicinfo.repositories.AtmRepository;
import com.bank.publicinfo.repositories.BranchRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AtmServiceImpl implements AtmService {
    private final AtmRepository atmRepository;
    private final BranchRepository branchRepository;
    private final AtmMapper atmMapper;

    @Transactional
    @Override
    public AtmDto create(AtmDto dto) {
        ATM atm = atmMapper.toEntity(dto);

        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new EntityNotFoundException("Отделение с id " + dto.getBranchId() + " не найдено"));
        atm.setBranch(branch);

        ATM saved = atmRepository.save(atm);
        return atmMapper.toDto(saved);
    }

    @Transactional
    @Override
    public AtmDto update(Long id, AtmDto dto) {
        ATM existing = atmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Банкомат с id " + id + " не найден"));

        existing.setAddress(dto.getAddress());
        existing.setStartOfWork(dto.getStartOfWork());
        existing.setEndOfWork(dto.getEndOfWork());
        existing.setAllHours(dto.getAllHours());

        Long existingBranchId = Optional.ofNullable(existing.getBranch())
                .map(Branch::getId)
                .orElse(null);

        if (!Objects.equals(existingBranchId, dto.getBranchId())) {
            Branch newBranch = branchRepository.findById(dto.getBranchId())
                    .orElseThrow(() -> new EntityNotFoundException("Отделение с id " + dto.getBranchId() + " не найдено"));
            existing.setBranch(newBranch);
        }

        return atmMapper.toDto(atmRepository.save(existing));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        ATM atm = atmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Банкомат с id " + id + " не найден"));

        atmRepository.delete(atm);
    }

    @Transactional(readOnly = true)
    @Override
    public AtmDto getById(Long id) {
        return atmRepository.findById(id)
                .map(atmMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Банкомат с id " + id + " не найден"));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AtmDto> getAll(Pageable pageable) {
        return atmRepository.findAll(pageable)
                .map(atmMapper::toDto);
    }
}
