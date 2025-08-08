package com.bank.publicinfo.services;

import com.bank.publicinfo.Mappers.LicenseMapper;
import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entities.BankDetails;
import com.bank.publicinfo.entities.License;
import com.bank.publicinfo.repositories.BankDetailsRepository;
import com.bank.publicinfo.repositories.LicenseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class LicenseServiceImplTest {

    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 404L;
    private static final byte[] PHOTO = new byte[] {1, 2, 3, 4, 5};
    private static final String ENTITY_NOT_FOUND_MSG = "Лицензия с id " + INVALID_ID + " не найдена";
    private static final String BANK_NOT_FOUND_MSG = "Банк с id " + INVALID_ID + " не найден";

    @Mock private LicenseRepository licenseRepository;
    @Mock private BankDetailsRepository bankDetailsRepository;
    @Mock private LicenseMapper licenseMapper;

    @InjectMocks private LicenseServiceImpl licenseService;

    private LicenseDto dto;
    private License entity;
    private License savedEntity;
    private LicenseDto resultDto;
    private BankDetails bank;

    @BeforeEach
    void init() {
        dto = new LicenseDto();
        dto.setBankDetailsId(VALID_ID);
        dto.setPhoto(PHOTO);

        entity = new License();
        entity.setPhoto(PHOTO);

        savedEntity = new License();
        savedEntity.setPhoto(PHOTO);

        resultDto = new LicenseDto();
        resultDto.setPhoto(PHOTO);

        bank = new BankDetails();
        bank.setId(VALID_ID);
    }

    @Test
    void create_shouldCreateAndReturnDto() {
        when(licenseMapper.toEntity(dto)).thenReturn(entity);
        lenient().when(bankDetailsRepository.findById(VALID_ID)).thenReturn(Optional.of(bank));
        when(licenseRepository.save(entity)).thenReturn(savedEntity);
        when(licenseMapper.toDto(savedEntity)).thenReturn(resultDto);

        LicenseDto actual = licenseService.create(dto);

        assertEquals(resultDto, actual);
        verify(licenseRepository).save(entity);
    }

    @Test
    void create_shouldThrowIfBankNotFound() {
        dto.setBankDetailsId(INVALID_ID);

        when(licenseMapper.toEntity(dto)).thenReturn(entity);
        when(bankDetailsRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> licenseService.create(dto));

        assertTrue(exception.getMessage().contains(String.valueOf(INVALID_ID)));
    }

    @Test
    void update_shouldUpdateAndReturnDto() {
        License existing = new License();
        existing.setBankDetails(bank);

        when(licenseRepository.findById(VALID_ID)).thenReturn(Optional.of(existing));
        lenient().when(bankDetailsRepository.findById(VALID_ID)).thenReturn(Optional.of(bank));
        when(licenseRepository.save(existing)).thenReturn(savedEntity);
        when(licenseMapper.toDto(savedEntity)).thenReturn(resultDto);

        LicenseDto actual = licenseService.update(VALID_ID, dto);

        assertEquals(PHOTO, existing.getPhoto());
        assertEquals(resultDto, actual);
    }

    @Test
    void update_shouldThrowIfLicenseNotFound() {
        when(licenseRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> licenseService.update(INVALID_ID, dto));

        assertEquals(ENTITY_NOT_FOUND_MSG, exception.getMessage());
    }

    @Test
    void update_shouldThrowIfNewBankNotFound() {
        License existing = new License();
        existing.setBankDetails(new BankDetails() {{ setId(VALID_ID + 1); }});

        dto.setBankDetailsId(INVALID_ID);

        when(licenseRepository.findById(VALID_ID)).thenReturn(Optional.of(existing));
        when(bankDetailsRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> licenseService.update(VALID_ID, dto));

        assertEquals(BANK_NOT_FOUND_MSG, exception.getMessage());
    }

    @Test
    void delete_shouldDeleteLicense() {
        License license = new License();
        when(licenseRepository.findById(VALID_ID)).thenReturn(Optional.of(license));

        licenseService.delete(VALID_ID);

        verify(licenseRepository).delete(license);
    }

    @Test
    void delete_shouldThrowIfNotFound() {
        when(licenseRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> licenseService.delete(INVALID_ID));

        assertEquals(ENTITY_NOT_FOUND_MSG, exception.getMessage());
    }

    @Test
    void getById_shouldReturnDto() {
        License license = new License();

        when(licenseRepository.findById(VALID_ID)).thenReturn(Optional.of(license));
        when(licenseMapper.toDto(license)).thenReturn(resultDto);

        LicenseDto result = licenseService.getById(VALID_ID);

        assertEquals(resultDto, result);
    }

    @Test
    void getById_shouldThrowIfNotFound() {
        when(licenseRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> licenseService.getById(INVALID_ID));

        assertEquals(ENTITY_NOT_FOUND_MSG, exception.getMessage());
    }

    @Test
    void getAll_shouldReturnPage() {
        License license = new License();
        Page<License> page = new PageImpl<>(List.of(license));

        when(licenseRepository.findAll(Pageable.unpaged())).thenReturn(page);
        when(licenseMapper.toDto(license)).thenReturn(resultDto);

        Page<LicenseDto> result = licenseService.getAll(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertEquals(resultDto, result.getContent().get(0));
    }
}
