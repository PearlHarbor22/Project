package com.bank.profile.service.impl;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsId;
import com.bank.profile.mapper.AccountDetailsIdMapper;
import com.bank.profile.repository.AccountDetailsIdRepository;
import com.bank.profile.service.AccountDetailsIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDetailsIdServiceImpl implements AccountDetailsIdService {

    private final AccountDetailsIdRepository repository;

    @Qualifier("accountDetailsIdMapper")
    private final AccountDetailsIdMapper mapper;

    @Override
    public AccountDetailsIdDto save(AccountDetailsIdDto dto) {
        AccountDetailsId entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public AccountDetailsIdDto update(Long id, AccountDetailsIdDto dto) {
        AccountDetailsId existing = repository.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("AccountDetailsId не найден: id=" + id));
        AccountDetailsId updated = mapper.toEntity(dto);
        updated.setId(existing.getId());
        return mapper.toDto(repository.save(updated));
    }

    @Override
    public AccountDetailsIdDto findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("AccountDetailsId не найден: id=" + id));
    }

    @Override
    public List<AccountDetailsIdDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new jakarta.persistence.EntityNotFoundException("AccountDetailsId не найден: id=" + id);
        }
        repository.deleteById(id);
    }
} 