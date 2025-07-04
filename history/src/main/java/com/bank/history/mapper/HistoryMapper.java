package com.bank.history.mapper;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Маппер для преобразования между HistoryEntity и HistoryDto.
 */


@Mapper(componentModel = "spring")
public interface HistoryMapper {

    /**
     * Преобразует HistoryEntity в HistoryDto.
     */

    HistoryDto toDto(HistoryEntity entity);

    /**
     * Преобразует HistoryDto в HistoryEntity.
     */

    HistoryEntity toEntity(HistoryDto dto);

    /**
     * Преобразует список сущностей HistoryEntity в список DTO HistoryDto.
     *
     * @param entities список сущностей
     * @return список DTO
     */
    List<HistoryDto> toDtoList(List<HistoryEntity> entities);
}



