package com.bank.publicinfo.services;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entities.BankDetails;
import com.bank.publicinfo.entities.Branch;
import com.bank.publicinfo.Mappers.BranchMapper;
import com.bank.publicinfo.repositories.BankDetailsRepository;
import com.bank.publicinfo.repositories.BranchRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final BankDetailsRepository bankDetailsRepository;
    private final BranchMapper branchMapper;

    @Override
    public BranchDto create(BranchDto dto) {
        Branch branch = branchMapper.toEntity(dto);

        BankDetails bankDetails = bankDetailsRepository.findById(dto.getBankDetailsId())
                .orElseThrow(() -> new EntityNotFoundException("Банк с id " + dto.getBankDetailsId() + " не найден"));
        branch.setBankDetails(bankDetails);

        Branch saved = branchRepository.save(branch);
        return branchMapper.toDto(saved);
    }

    @Override
    public BranchDto update(Long id, BranchDto dto) {
        Branch existing = branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Отделение с id " + id + " не найдено"));

        existing.setAddress(dto.getAddress());
        existing.setPhoneNumber(dto.getPhoneNumber());
        existing.setCity(dto.getCity());
        existing.setStartOfWork(dto.getStartOfWork());
        existing.setEndOfWork(dto.getEndOfWork());

        if (!existing.getBankDetails().getId().equals(dto.getBankDetailsId())) {
            BankDetails newBank = bankDetailsRepository.findById(dto.getBankDetailsId())
                    .orElseThrow(() -> new EntityNotFoundException("Банк с id " + dto.getBankDetailsId() + " не найден"));
            existing.setBankDetails(newBank);
        }

        return branchMapper.toDto(branchRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!branchRepository.existsById(id)) {
            throw new EntityNotFoundException("Отделение с id " + id + " не найдено");
        }
        branchRepository.deleteById(id);
    }

    @Override
    public BranchDto getById(Long id) {
        return branchRepository.findById(id)
                .map(branchMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Отделение с id " + id + " не найдено"));
    }

    @Override
    public List<BranchDto> getAll() {
        return branchRepository.findAll()
                .stream()
                .map(branchMapper::toDto)
                .collect(Collectors.toList());
    }


}
