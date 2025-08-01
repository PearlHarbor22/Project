package com.bank.publicinfo.services;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entities.BankDetails;
import com.bank.publicinfo.entities.Branch;
import com.bank.publicinfo.Mappers.BranchMapper;
import com.bank.publicinfo.repositories.BankDetailsRepository;
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
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final BankDetailsRepository bankDetailsRepository;
    private final BranchMapper branchMapper;

    @Transactional
    @Override
    public BranchDto create(BranchDto dto) {
        Branch branch = branchMapper.toEntity(dto);

        BankDetails bankDetails = bankDetailsRepository.findById(dto.getBankDetailsId())
                .orElseThrow(() -> new EntityNotFoundException("Банк с id " + dto.getBankDetailsId() + " не найден"));
        branch.setBankDetails(bankDetails);

        Branch saved = branchRepository.save(branch);
        return branchMapper.toDto(saved);
    }

    @Transactional
    @Override
    public BranchDto update(Long id, BranchDto dto) {
        Branch existing = branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Отделение с id " + id + " не найдено"));

        existing.setAddress(dto.getAddress());
        existing.setPhoneNumber(dto.getPhoneNumber());
        existing.setCity(dto.getCity());
        existing.setStartOfWork(dto.getStartOfWork());
        existing.setEndOfWork(dto.getEndOfWork());

        Long existingBankId = Optional.ofNullable(existing.getBankDetails())
                .map(BankDetails::getId)
                .orElse(null);

        if (!Objects.equals(existingBankId, dto.getBankDetailsId())) {
            BankDetails newBank = bankDetailsRepository.findById(dto.getBankDetailsId())
                    .orElseThrow(() -> new EntityNotFoundException("Банк с id " + dto.getBankDetailsId() + " не найден"));
            existing.setBankDetails(newBank);
        }

        return branchMapper.toDto(branchRepository.save(existing));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Отделение с id " + id + " не найдено"));

        branchRepository.delete(branch);
    }

    @Transactional(readOnly = true)
    @Override
    public BranchDto getById(Long id) {
        return branchRepository.findById(id)
                .map(branchMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Отделение с id " + id + " не найдено"));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BranchDto> getAll(Pageable pageable) {
        return branchRepository.findAll(pageable)
                .map(branchMapper::toDto);
    }
}
