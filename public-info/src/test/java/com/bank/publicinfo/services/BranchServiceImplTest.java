package com.bank.publicinfo.services;

import com.bank.publicinfo.Mappers.BranchMapper;
import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entities.BankDetails;
import com.bank.publicinfo.entities.Branch;
import com.bank.publicinfo.repositories.BankDetailsRepository;
import com.bank.publicinfo.repositories.BranchRepository;
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
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class BranchServiceImplTest {

    private static final Long EXISTING_BRANCH_ID = 1L;
    private static final Long NON_EXISTING_BRANCH_ID = 404L;

    private static final Long OLD_BANK_ID = 10L;
    private static final Long NEW_BANK_ID = 20L;

    private static final String ADDRESS = "ул. Центральная, 1";
    private static final String CITY = "Москва";
    private static final Long PHONE = 1234567890L;
    private static final LocalTime START = LocalTime.of(9, 0);
    private static final LocalTime END = LocalTime.of(18, 0);

    @Mock
    private BranchRepository branchRepository;
    @Mock
    private BankDetailsRepository bankDetailsRepository;
    @Mock
    private BranchMapper branchMapper;

    @InjectMocks
    private BranchServiceImpl branchService;

    private BranchDto inputDto;
    private BranchDto expectedDto;
    private Branch branchEntity;

    @BeforeEach
    void setUp() {
        inputDto = new BranchDto();
        inputDto.setBankDetailsId(NEW_BANK_ID);
        inputDto.setAddress(ADDRESS);
        inputDto.setCity(CITY);
        inputDto.setPhoneNumber(PHONE);
        inputDto.setStartOfWork(START);
        inputDto.setEndOfWork(END);

        expectedDto = new BranchDto();
        branchEntity = mock(Branch.class);
    }

    @Test
    void create_ShouldSetBankAndReturnDto() {
        BankDetails bankDetails = new BankDetails();

        when(branchMapper.toEntity(inputDto)).thenReturn(branchEntity);
        when(bankDetailsRepository.findById(NEW_BANK_ID)).thenReturn(Optional.of(bankDetails));
        when(branchRepository.save(branchEntity)).thenReturn(branchEntity);
        when(branchMapper.toDto(branchEntity)).thenReturn(expectedDto);

        BranchDto result = branchService.create(inputDto);

        assertEquals(expectedDto, result);
        verify(branchEntity).setBankDetails(bankDetails);
    }

    @Test
    void create_WhenBankNotFound_ShouldThrowException() {
        when(branchMapper.toEntity(inputDto)).thenReturn(branchEntity);
        when(bankDetailsRepository.findById(NEW_BANK_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> branchService.create(inputDto));
    }

    @Test
    void update_WhenBankChanged_ShouldUpdateAndReturnDto() {
        BankDetails oldBank = new BankDetails();
        oldBank.setId(OLD_BANK_ID);
        BankDetails newBank = new BankDetails();
        newBank.setId(NEW_BANK_ID);

        Branch existing = new Branch();
        existing.setBankDetails(oldBank);

        when(branchRepository.findById(EXISTING_BRANCH_ID)).thenReturn(Optional.of(existing));
        when(bankDetailsRepository.findById(NEW_BANK_ID)).thenReturn(Optional.of(newBank));
        when(branchRepository.save(existing)).thenReturn(branchEntity);
        when(branchMapper.toDto(branchEntity)).thenReturn(expectedDto);

        BranchDto result = branchService.update(EXISTING_BRANCH_ID, inputDto);

        assertEquals(expectedDto, result);
        assertEquals(newBank, existing.getBankDetails());
    }

    @Test
    void update_WhenBankSame_ShouldSkipBankUpdate() {
        BankDetails sameBank = new BankDetails();
        sameBank.setId(NEW_BANK_ID);

        Branch existing = new Branch();
        existing.setBankDetails(sameBank);

        when(branchRepository.findById(EXISTING_BRANCH_ID)).thenReturn(Optional.of(existing));
        when(branchRepository.save(existing)).thenReturn(branchEntity);
        when(branchMapper.toDto(branchEntity)).thenReturn(expectedDto);

        BranchDto result = branchService.update(EXISTING_BRANCH_ID, inputDto);

        assertEquals(expectedDto, result);
        verify(bankDetailsRepository, never()).findById(anyLong());
    }

    @Test
    void update_WhenBranchNotFound_ShouldThrowException() {
        when(branchRepository.findById(NON_EXISTING_BRANCH_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> branchService.update(NON_EXISTING_BRANCH_ID, inputDto));
    }

    @Test
    void update_WhenNewBankNotFound_ShouldThrowException() {
        BankDetails oldBank = new BankDetails();
        oldBank.setId(OLD_BANK_ID);
        Branch existing = new Branch();
        existing.setBankDetails(oldBank);

        when(branchRepository.findById(EXISTING_BRANCH_ID)).thenReturn(Optional.of(existing));
        when(bankDetailsRepository.findById(NEW_BANK_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> branchService.update(EXISTING_BRANCH_ID, inputDto));
    }

    @Test
    void delete_WhenFound_ShouldDeleteBranch() {
        Branch branch = new Branch();
        when(branchRepository.findById(EXISTING_BRANCH_ID)).thenReturn(Optional.of(branch));

        branchService.delete(EXISTING_BRANCH_ID);

        verify(branchRepository).delete(branch);
    }

    @Test
    void delete_WhenNotFound_ShouldThrowException() {
        when(branchRepository.findById(NON_EXISTING_BRANCH_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> branchService.delete(NON_EXISTING_BRANCH_ID));
    }

    @Test
    void getById_WhenFound_ShouldReturnDto() {
        when(branchRepository.findById(EXISTING_BRANCH_ID)).thenReturn(Optional.of(branchEntity));
        when(branchMapper.toDto(branchEntity)).thenReturn(expectedDto);

        BranchDto result = branchService.getById(EXISTING_BRANCH_ID);

        assertEquals(expectedDto, result);
    }

    @Test
    void getById_WhenNotFound_ShouldThrowException() {
        when(branchRepository.findById(NON_EXISTING_BRANCH_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> branchService.getById(NON_EXISTING_BRANCH_ID));
    }

    @Test
    void getAll_ShouldReturnPagedDtoList() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Branch> branchPage = new PageImpl<>(List.of(branchEntity));

        when(branchRepository.findAll(pageable)).thenReturn(branchPage);
        when(branchMapper.toDto(branchEntity)).thenReturn(expectedDto);

        Page<BranchDto> result = branchService.getAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(expectedDto, result.getContent().get(0));
    }
}
