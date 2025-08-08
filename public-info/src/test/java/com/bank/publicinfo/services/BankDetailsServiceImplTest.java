package com.bank.publicinfo.services;

import com.bank.publicinfo.Mappers.BankDetailsMapper;
import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entities.BankDetails;
import com.bank.publicinfo.kafkaProducer.BankDetailsProducer;
import com.bank.publicinfo.repositories.BankDetailsRepository;
import com.bank.publicinfo.services.BankDetailsServiceImpl;
import jakarta.persistence.EntityNotFoundException;
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
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankDetailsServiceImplTest {

    private static final Long EXISTING_ID = 1L;
    private static final Long NON_EXISTING_ID = 999L;
    private static final Long BIK = 123456789L;
    private static final Long INN = 987654321L;
    private static final Long KPP = 111222333L;
    private static final String COR_ACCOUNT = "12345678901234567890";
    private static final String CITY = "TestCity";
    private static final String JSC = "TestJSC";
    private static final String BANK_NAME = "TestBank";
    private static final int PAGE = 0;
    private static final int SIZE = 10;

    @Mock
    private BankDetailsRepository repository;

    @Mock
    private BankDetailsMapper mapper;

    @Mock
    private BankDetailsProducer producer;

    @InjectMocks
    private BankDetailsServiceImpl service;

    @Test
    void testCreate_shouldSucceed() {
        BankDetailsDto dto = new BankDetailsDto();
        BankDetails entity = new BankDetails();
        BankDetails saved = new BankDetails();
        BankDetailsDto resultDto = new BankDetailsDto();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(resultDto);

        BankDetailsDto result = service.create(dto);

        assertEquals(resultDto, result);
        verify(producer).sendCreate(resultDto);
    }

    @Test
    void testUpdate_shouldSucceed() {
        BankDetailsDto dto = new BankDetailsDto();
        dto.setBik(BIK); dto.setInn(INN); dto.setKpp(KPP);
        dto.setCorAccount(COR_ACCOUNT); dto.setCity(CITY); dto.setJointStockCompany(JSC); dto.setName(BANK_NAME);

        BankDetails existing = new BankDetails();
        BankDetails updated = new BankDetails();
        BankDetailsDto updatedDto = new BankDetailsDto();

        when(repository.findById(EXISTING_ID)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(updated);
        when(mapper.toDto(updated)).thenReturn(updatedDto);

        BankDetailsDto result = service.update(EXISTING_ID, dto);

        assertEquals(updatedDto, result);
        verify(producer).sendUpdate(updatedDto);
    }

    @Test
    void testUpdate_notFound_shouldThrow() {
        when(repository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.update(NON_EXISTING_ID, new BankDetailsDto()));
    }

    @Test
    void testDelete_shouldSucceed() {
        BankDetails entity = new BankDetails();
        when(repository.findById(EXISTING_ID)).thenReturn(Optional.of(entity));

        service.delete(EXISTING_ID);

        verify(repository).delete(entity);
        verify(producer).sendDelete(argThat(dto -> dto.getId().equals(EXISTING_ID)));
    }

    @Test
    void testDelete_notFound_shouldThrow() {
        when(repository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.delete(NON_EXISTING_ID));
    }

    @Test
    void testGetById_shouldReturnDto() {
        BankDetails entity = new BankDetails();
        BankDetailsDto dto = new BankDetailsDto();

        when(repository.findById(EXISTING_ID)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        BankDetailsDto result = service.getById(EXISTING_ID);

        assertEquals(dto, result);
    }

    @Test
    void testGetById_notFound_shouldThrow() {
        when(repository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getById(NON_EXISTING_ID));
    }

    @Test
    void testGetAll_shouldReturnPageOfDtos() {
        BankDetails entity = new BankDetails();
        BankDetailsDto dto = new BankDetailsDto();
        Page<BankDetails> entityPage = new PageImpl<>(List.of(entity));
        Pageable pageable = PageRequest.of(PAGE, SIZE);

        when(repository.findAll(pageable)).thenReturn(entityPage);
        when(mapper.toDto(entity)).thenReturn(dto);

        Page<BankDetailsDto> result = service.getAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(dto, result.getContent().get(0));
    }
}
