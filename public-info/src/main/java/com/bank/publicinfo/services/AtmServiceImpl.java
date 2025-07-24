package com.bank.publicinfo.services;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entities.ATM;
import com.bank.publicinfo.entities.Branch;
import com.bank.publicinfo.Mappers.AtmMapper;
import com.bank.publicinfo.repositories.AtmRepository;
import com.bank.publicinfo.repositories.BranchRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AtmServiceImpl implements AtmService {
    private final AtmRepository atmRepository;
    private final BranchRepository branchRepository;
    private final AtmMapper atmMapper;

    @Override
    public AtmDto create(AtmDto dto) {
        ATM atm = atmMapper.toEntity(dto);

        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new EntityNotFoundException("Отделение с id " + dto.getBranchId() + " не найдено"));
        atm.setBranch(branch);
        atmRepository.save(atm);

        return atmMapper.toDto(atmRepository.save(atm));
    }

    @Override
    public AtmDto update(Long id, AtmDto dto) {
        ATM existing = atmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Банкомат с id " + id + " не найден"));

        existing.setAddress(dto.getAddress());
        existing.setStartOfWork(dto.getStartOfWork());
        existing.setEndOfWork(dto.getEndOfWork());
        existing.setAllHours(dto.getAllHours());

        if (!existing.getBranch().getId().equals(dto.getBranchId())) {
            Branch newBranch = branchRepository.findById(dto.getBranchId())
                    .orElseThrow(() -> new EntityNotFoundException("Отделение с id " + dto.getBranchId() + " не найдено"));
            existing.setBranch(newBranch);
        }

        return atmMapper.toDto(atmRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!atmRepository.existsById(id)) {
            throw new EntityNotFoundException("Банкомат с id " + id + " не найден");
        }
        atmRepository.deleteById(id);
    }

    @Override
    public AtmDto getById(Long id) {
        return atmRepository.findById(id)
                .map(atmMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Банкомат с id " + id + " не найден"));
    }

    @Override
    public List<AtmDto> getAll() {
        return atmRepository.findAll()
                .stream()
                .map(atmMapper::toDto)
                .collect(Collectors.toList());
    }

}
