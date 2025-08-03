package com.bank.profile.service.impl;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsId;
import com.bank.profile.mapper.AccountDetailsIdMapper;
import com.bank.profile.repository.AccountDetailsIdRepository;
import com.bank.profile.service.AccountDetailsIdService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountDetailsIdServiceImpl implements AccountDetailsIdService {

    private final AccountDetailsIdRepository repository;
    private final AccountDetailsIdMapper mapper;

    public AccountDetailsIdServiceImpl(
            AccountDetailsIdRepository repository,
            @Qualifier("accountDetailsIdMapperImpl") AccountDetailsIdMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public AccountDetailsIdDto save(AccountDetailsIdDto dto) {
        AccountDetailsId entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
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
    public Page<AccountDetailsIdDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("AccountDetailsId не найден: id=" + id);
        }
    }
} 