package com.bank.history.mapper;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    HistoryDto toDto(HistoryEntity entity);

    HistoryEntity toEntity(HistoryDto dto);

    List<HistoryDto> toDtoList(List<HistoryEntity> entities);
}
