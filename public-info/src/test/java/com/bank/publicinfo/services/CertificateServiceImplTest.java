package com.bank.publicinfo.services;

import com.bank.publicinfo.Mappers.CertificateMapper;
import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entities.BankDetails;
import com.bank.publicinfo.entities.Certificate;
import com.bank.publicinfo.repositories.BankDetailsRepository;
import com.bank.publicinfo.repositories.CertificateRepository;
import com.bank.publicinfo.services.CertificateServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class CertificateServiceImplTest {

    private static final Long EXISTING_ID = 1L;
    private static final Long NON_EXISTENT_ID = 999L;
    private static final byte[] SAMPLE_PHOTO = "fake-photo".getBytes();

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private BankDetailsRepository bankDetailsRepository;

    @Mock
    private CertificateMapper certificateMapper;

    @InjectMocks
    private CertificateServiceImpl certificateService;

    private CertificateDto dto;
    private Certificate entity;
    private BankDetails bank;

    @BeforeEach
    void setUp() {
        dto = new CertificateDto();
        dto.setPhoto(SAMPLE_PHOTO);
        dto.setBankDetailsId(EXISTING_ID);

        bank = new BankDetails();
        bank.setId(EXISTING_ID);

        entity = new Certificate();
        entity.setId(EXISTING_ID);
        entity.setPhoto(SAMPLE_PHOTO);
        entity.setBankDetails(bank);
    }

    @Test
    void create_shouldSaveAndReturnDto() {
        when(certificateMapper.toEntity(dto)).thenReturn(entity);
        lenient().when(bankDetailsRepository.findById(EXISTING_ID)).thenReturn(Optional.of(bank));
        when(certificateRepository.save(entity)).thenReturn(entity);
        when(certificateMapper.toDto(entity)).thenReturn(dto);

        CertificateDto result = certificateService.create(dto);

        assertEquals(dto, result);
        verify(certificateRepository).save(entity);
    }

    @Test
    void create_shouldThrowWhenBankNotFound() {
        when(certificateMapper.toEntity(dto)).thenReturn(entity);
        when(bankDetailsRepository.findById(EXISTING_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> certificateService.create(dto));
    }

    @Test
    void update_shouldUpdateAndReturnDto() {
        Certificate updated = new Certificate();
        updated.setId(EXISTING_ID);
        updated.setPhoto(SAMPLE_PHOTO);
        updated.setBankDetails(bank);

        when(certificateRepository.findById(EXISTING_ID)).thenReturn(Optional.of(entity));
        lenient().when(bankDetailsRepository.findById(EXISTING_ID)).thenReturn(Optional.of(bank));
        when(certificateRepository.save(entity)).thenReturn(updated);
        when(certificateMapper.toDto(updated)).thenReturn(dto);

        CertificateDto result = certificateService.update(EXISTING_ID, dto);

        assertEquals(dto, result);
        verify(certificateRepository).save(entity);
    }

    @Test
    void update_shouldThrowWhenCertificateNotFound() {
        when(certificateRepository.findById(EXISTING_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> certificateService.update(EXISTING_ID, dto));
    }

    @Test
    void update_shouldThrowWhenNewBankNotFound() {
        BankDetails oldBank = new BankDetails();
        oldBank.setId(NON_EXISTENT_ID);
        entity.setBankDetails(oldBank);

        when(certificateRepository.findById(EXISTING_ID)).thenReturn(Optional.of(entity));
        when(bankDetailsRepository.findById(EXISTING_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> certificateService.update(EXISTING_ID, dto));
    }

    @Test
    void delete_shouldRemoveEntity() {
        when(certificateRepository.findById(EXISTING_ID)).thenReturn(Optional.of(entity));

        certificateService.delete(EXISTING_ID);

        verify(certificateRepository).delete(entity);
    }

    @Test
    void delete_shouldThrowWhenNotFound() {
        when(certificateRepository.findById(EXISTING_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> certificateService.delete(EXISTING_ID));
    }

    @Test
    void getById_shouldReturnDto() {
        when(certificateRepository.findById(EXISTING_ID)).thenReturn(Optional.of(entity));
        when(certificateMapper.toDto(entity)).thenReturn(dto);

        CertificateDto result = certificateService.getById(EXISTING_ID);

        assertEquals(dto, result);
    }

    @Test
    void getById_shouldThrowWhenNotFound() {
        when(certificateRepository.findById(EXISTING_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> certificateService.getById(EXISTING_ID));
    }

    @Test
    void getAll_shouldReturnPagedDtos() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Certificate> entityPage = new PageImpl<>(List.of(entity));
        CertificateDto anotherDto = new CertificateDto();

        when(certificateRepository.findAll(pageable)).thenReturn(entityPage);
        when(certificateMapper.toDto(entity)).thenReturn(anotherDto);

        Page<CertificateDto> result = certificateService.getAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(anotherDto, result.getContent().get(0));
    }
}
