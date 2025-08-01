package com.bank.history.service;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import com.bank.history.mapper.HistoryMapper;
import com.bank.history.repository.HistoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.mock;


public class HistoryServiceImplTest {

    private final HistoryRepository repository = mock(HistoryRepository.class);
    private final HistoryMapper mapper = mock(HistoryMapper.class);
    private final HistoryServiceImpl service = new HistoryServiceImpl(repository, mapper);

    private static final String ERROR_MESSAGE = "DB error";

    @Test
    void save_Valid() {
        HistoryDto dto = new HistoryDto();
        HistoryEntity entity = new HistoryEntity();

        when(mapper.toEntity(dto)).thenReturn(entity);

        service.save(dto);

        verify(mapper).toEntity(dto);
        verify(repository).save(entity);
    }

    @Test
    void save_Null() {
        service.save(null);
        verifyNoInteractions(repository);
        verifyNoInteractions(mapper);
    }

    @Test
    void getAll_List() {
        List<HistoryEntity> entities = Collections.singletonList(new HistoryEntity());
        List<HistoryDto> dtos = Collections.singletonList(new HistoryDto());

        when(repository.findAll()).thenReturn(entities);
        when(mapper.toDtoList(entities)).thenReturn(dtos);

        List<HistoryDto> result = service.getAll();

        verify(repository).findAll();
        verify(mapper).toDtoList(entities);
        Assertions.assertEquals(dtos, result);
    }

    @Test
    void save_Exception() {
        HistoryDto dto = new HistoryDto();
        HistoryEntity entity = new HistoryEntity();

        Mockito.when(mapper.toEntity(dto)).thenReturn(entity);
        Mockito.doThrow(new RuntimeException(ERROR_MESSAGE)).when(repository).save(entity);

        Assertions.assertDoesNotThrow(() -> service.save(dto));

        Mockito.verify(mapper).toEntity(dto);
        Mockito.verify(repository).save(entity);
    }
}