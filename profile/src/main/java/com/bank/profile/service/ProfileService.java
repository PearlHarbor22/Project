package com.bank.profile.service;

import com.bank.profile.dto.ProfileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface ProfileService {

    ProfileDto save(ProfileDto dto);

    ProfileDto update(Long id, ProfileDto dto);

    ProfileDto findById(Long id);

    Page<ProfileDto> findAll(Pageable pageable);

    void delete(Long id);
}
