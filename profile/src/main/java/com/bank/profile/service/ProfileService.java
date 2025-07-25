package com.bank.profile.service;

import com.bank.profile.dto.ProfileDto;

import java.util.List;

public interface ProfileService {

    ProfileDto save(ProfileDto dto);

    ProfileDto update(Long id, ProfileDto dto);

    ProfileDto findById(Long id);

    List<ProfileDto> findAll();

    void delete(Long id);
}
