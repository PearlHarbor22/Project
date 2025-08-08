package com.bank.publicinfo.services;

import com.bank.publicinfo.Mappers.CertificateMapper;
import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entities.BankDetails;
import com.bank.publicinfo.entities.Certificate;
import com.bank.publicinfo.repositories.BankDetailsRepository;
import com.bank.publicinfo.repositories.CertificateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CertificateServiceImplTest {

    private static final Long EXISTING_ID = 1L;
    private static final Long NON_EXISTENT_ID = 999L;
    private static final byte[] SAMPLE_PHOTO = "fake-photo".getBytes();
    private static final String PHOTO_UPDATE_TEXT = "Поле photo должно быть обновлено";
    private static final String BANK_UPDATE_TEXT = "BankDetails должен быть обновлён";
    private static final int PAGE = 0;
    private static final int SIZE = 5;
    private static final int FIRST_INDEX = 0;
    private static final int EXPECTED_TOTAL_ELEMENTS = 1;

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
    private CertificateDto expectedDto;

    @Captor
    private ArgumentCaptor<Certificate> certificateCaptor;

    @BeforeEach
    void setUp() {
        dto = new CertificateDto();
        dto.setPhoto(SAMPLE_PHOTO);
        dto.setBankDetailsId(EXISTING_ID);

        expectedDto = new CertificateDto();
        expectedDto.setPhoto(SAMPLE_PHOTO);
        expectedDto.setBankDetailsId(EXISTING_ID);

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
        when(bankDetailsRepository.findById(EXISTING_ID)).thenReturn(Optional.of(bank));
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

        dto.setPhoto(SAMPLE_PHOTO);
        dto.setBankDetailsId(EXISTING_ID);

        when(certificateRepository.findById(EXISTING_ID)).thenReturn(Optional.of(entity));
        lenient().when(bankDetailsRepository.findById(EXISTING_ID)).thenReturn(Optional.of(bank));
        when(certificateRepository.save(any(Certificate.class))).thenReturn(updated);
        when(certificateMapper.toDto(updated)).thenReturn(dto);

        CertificateDto result = certificateService.update(EXISTING_ID, dto);

        assertEquals(dto, result);

        verify(certificateRepository).save(certificateCaptor.capture());
        Certificate captured = certificateCaptor.getValue();

        assertEquals(SAMPLE_PHOTO, captured.getPhoto(), PHOTO_UPDATE_TEXT);
        assertEquals(bank, captured.getBankDetails(), BANK_UPDATE_TEXT);
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
        Pageable pageable = PageRequest.of(PAGE, SIZE);
        Page<Certificate> entityPage = new PageImpl<>(List.of(entity));

        when(certificateRepository.findAll(pageable)).thenReturn(entityPage);
        when(certificateMapper.toDto(entity)).thenReturn(expectedDto);

        Page<CertificateDto> result = certificateService.getAll(pageable);

        assertEquals(EXPECTED_TOTAL_ELEMENTS, result.getTotalElements());
        assertEquals(expectedDto, result.getContent().get(FIRST_INDEX));
    }
}
