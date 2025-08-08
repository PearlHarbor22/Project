package com.bank.publicinfo.services;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entities.ATM;
import com.bank.publicinfo.entities.Branch;
import com.bank.publicinfo.Mappers.AtmMapper;
import com.bank.publicinfo.repositories.AtmRepository;
import com.bank.publicinfo.repositories.BranchRepository;
import com.bank.publicinfo.services.AtmServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AtmServiceImplTest {

    private static final Long EXISTING_ATM_ID = 1L;
    private static final Long NON_EXISTING_ID = 999L;
    private static final Long OLD_BRANCH_ID = 1L;
    private static final Long NEW_BRANCH_ID = 2L;
    private static final String NEW_ADDRESS = "New Address";
    private static final LocalTime START_OF_WORK = LocalTime.of(8, 0);
    private static final LocalTime END_OF_WORK = LocalTime.of(20, 0);

    @Mock
    private AtmRepository atmRepository;

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private AtmMapper atmMapper;

    @InjectMocks
    private AtmServiceImpl atmService;

    @Test
    void testCreateAtm_shouldCreateSuccessfully() {
        AtmDto dto = new AtmDto();
        dto.setBranchId(OLD_BRANCH_ID);

        ATM atmEntity = new ATM();
        Branch branch = new Branch(); branch.setId(OLD_BRANCH_ID);
        ATM savedAtm = new ATM();
        AtmDto resultDto = new AtmDto();

        Mockito.when(atmMapper.toEntity(dto)).thenReturn(atmEntity);
        Mockito.when(branchRepository.findById(OLD_BRANCH_ID)).thenReturn(Optional.of(branch));
        Mockito.when(atmRepository.save(atmEntity)).thenReturn(savedAtm);
        Mockito.when(atmMapper.toDto(savedAtm)).thenReturn(resultDto);

        AtmDto result = atmService.create(dto);

        Assertions.assertEquals(resultDto, result);
    }

    @Test
    void testCreateAtm_branchNotFound_shouldThrow() {
        AtmDto dto = new AtmDto();
        dto.setBranchId(NON_EXISTING_ID);

        ATM atmEntity = new ATM();
        Mockito.when(atmMapper.toEntity(dto)).thenReturn(atmEntity);
        Mockito.when(branchRepository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> atmService.create(dto));
    }

    @Test
    void testUpdateAtm_shouldUpdateFieldsAndBranch() {
        AtmDto dto = new AtmDto();
        dto.setAddress(NEW_ADDRESS);
        dto.setStartOfWork(START_OF_WORK);
        dto.setEndOfWork(END_OF_WORK);
        dto.setAllHours(true);
        dto.setBranchId(NEW_BRANCH_ID);

        ATM existingAtm = new ATM();
        Branch oldBranch = new Branch(); oldBranch.setId(OLD_BRANCH_ID);
        existingAtm.setBranch(oldBranch);

        Branch newBranch = new Branch(); newBranch.setId(NEW_BRANCH_ID);
        ATM updatedAtm = new ATM();
        AtmDto updatedDto = new AtmDto();

        Mockito.when(atmRepository.findById(EXISTING_ATM_ID)).thenReturn(Optional.of(existingAtm));
        Mockito.when(branchRepository.findById(NEW_BRANCH_ID)).thenReturn(Optional.of(newBranch));
        Mockito.when(atmRepository.save(existingAtm)).thenReturn(updatedAtm);
        Mockito.when(atmMapper.toDto(updatedAtm)).thenReturn(updatedDto);

        AtmDto result = atmService.update(EXISTING_ATM_ID, dto);

        Assertions.assertEquals(updatedDto, result);
    }

    @Test
    void testUpdateAtm_notFound_shouldThrow() {
        Mockito.when(atmRepository.findById(EXISTING_ATM_ID)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> atmService.update(EXISTING_ATM_ID, new AtmDto()));
    }

    @Test
    void testUpdateAtm_newBranchNotFound_shouldThrow() {
        ATM existing = new ATM();
        Branch oldBranch = new Branch(); oldBranch.setId(OLD_BRANCH_ID);
        existing.setBranch(oldBranch);

        AtmDto dto = new AtmDto(); dto.setBranchId(NEW_BRANCH_ID);

        Mockito.when(atmRepository.findById(EXISTING_ATM_ID)).thenReturn(Optional.of(existing));
        Mockito.when(branchRepository.findById(NEW_BRANCH_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> atmService.update(EXISTING_ATM_ID, dto));
    }

    @Test
    void testDeleteAtm_shouldDelete() {
        ATM atm = new ATM();
        Mockito.when(atmRepository.findById(EXISTING_ATM_ID)).thenReturn(Optional.of(atm));

        atmService.delete(EXISTING_ATM_ID);

        Mockito.verify(atmRepository).delete(atm);
    }

    @Test
    void testDeleteAtm_notFound_shouldThrow() {
        Mockito.when(atmRepository.findById(EXISTING_ATM_ID)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> atmService.delete(EXISTING_ATM_ID));
    }

    @Test
    void testGetById_shouldReturnDto() {
        ATM atm = new ATM();
        AtmDto dto = new AtmDto();

        Mockito.when(atmRepository.findById(EXISTING_ATM_ID)).thenReturn(Optional.of(atm));
        Mockito.when(atmMapper.toDto(atm)).thenReturn(dto);

        AtmDto result = atmService.getById(EXISTING_ATM_ID);
        Assertions.assertEquals(dto, result);
    }

    @Test
    void testGetById_notFound_shouldThrow() {
        Mockito.when(atmRepository.findById(EXISTING_ATM_ID)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> atmService.getById(EXISTING_ATM_ID));
    }

    @Test
    void testGetAll_shouldReturnPageOfDtos() {
        ATM atm = new ATM();
        AtmDto dto = new AtmDto();
        Page<ATM> page = new PageImpl<>(List.of(atm));

        Mockito.when(atmRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);
        Mockito.when(atmMapper.toDto(atm)).thenReturn(dto);

        Page<AtmDto> result = atmService.getAll(Pageable.unpaged());

        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(dto, result.getContent().get(0));
    }

}
