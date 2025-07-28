package com.bank.history.mapper;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class HistoryMapperTest {

    private final HistoryMapper mapper = Mappers.getMapper(HistoryMapper.class);

    @Test
    void toDto_MapsAllFieldsCorrectly() {
        HistoryEntity entity = HistoryEntity.builder()
                .id(1L)
                .transferAuditId(2L)
                .profileAuditId(3L)
                .accountAuditId(4L)
                .antiFraudAuditId(5L)
                .publicBankInfoAuditId(6L)
                .authorizationAuditId(7L)
                .build();

        HistoryDto dto = mapper.toDto(entity);

        Assertions.assertEquals(entity.getId(), dto.getId());
        Assertions.assertEquals(entity.getTransferAuditId(), dto.getTransferAuditId());
        Assertions.assertEquals(entity.getProfileAuditId(), dto.getProfileAuditId());
        Assertions.assertEquals(entity.getAccountAuditId(), dto.getAccountAuditId());
        Assertions.assertEquals(entity.getAntiFraudAuditId(), dto.getAntiFraudAuditId());
        Assertions.assertEquals(entity.getPublicBankInfoAuditId(), dto.getPublicBankInfoAuditId());
        Assertions.assertEquals(entity.getAuthorizationAuditId(), dto.getAuthorizationAuditId());
    }

    @Test
    void toEntity_MapsAllFieldsCorrectly() {
        HistoryDto dto = HistoryDto.builder()
                .id(10L)
                .transferAuditId(20L)
                .profileAuditId(30L)
                .accountAuditId(40L)
                .antiFraudAuditId(50L)
                .publicBankInfoAuditId(60L)
                .authorizationAuditId(70L)
                .build();

        HistoryEntity entity = mapper.toEntity(dto);

        Assertions.assertEquals(dto.getId(), entity.getId());
        Assertions.assertEquals(dto.getTransferAuditId(), entity.getTransferAuditId());
        Assertions.assertEquals(dto.getProfileAuditId(), entity.getProfileAuditId());
        Assertions.assertEquals(dto.getAccountAuditId(), entity.getAccountAuditId());
        Assertions.assertEquals(dto.getAntiFraudAuditId(), entity.getAntiFraudAuditId());
        Assertions.assertEquals(dto.getPublicBankInfoAuditId(), entity.getPublicBankInfoAuditId());
        Assertions.assertEquals(dto.getAuthorizationAuditId(), entity.getAuthorizationAuditId());
    }

    @Test
    void toDtoList_MapsListsCorrectly() {
        HistoryEntity entity1 = HistoryEntity.builder().id(1L).authorizationAuditId(7L).build();
        HistoryEntity entity2 = HistoryEntity.builder().id(10L).publicBankInfoAuditId(60L).build();

        var dtoList = mapper.toDtoList(List.of(entity1, entity2));

        Assertions.assertEquals(2, dtoList.size());
        Assertions.assertEquals(entity1.getId(), dtoList.get(0).getId());
        Assertions.assertEquals(entity1.getAuthorizationAuditId(), dtoList.get(0).getAuthorizationAuditId());
        Assertions.assertEquals(entity2.getId(), dtoList.get(1).getId());
        Assertions.assertEquals(entity2.getPublicBankInfoAuditId(), dtoList.get(1).getPublicBankInfoAuditId());
    }

    @Test
    void toDtoList_MapsSingleElementList() {
        HistoryEntity entity = HistoryEntity.builder().id(100L).build();
        var dtoList = mapper.toDtoList(List.of(entity));
        Assertions.assertEquals(1, dtoList.size());
        Assertions.assertEquals(100L, dtoList.get(0).getId());
    }

    @Test
    void toDto_NullInput_ReturnsNull() {
        Assertions.assertNull(mapper.toDto(null));
    }

    @Test
    void toEntity_NullInput_ReturnsNull() {
        Assertions.assertNull(mapper.toEntity(null));
    }
}
