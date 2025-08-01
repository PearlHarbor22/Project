package com.bank.history.mapper;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static com.bank.history.util.TestData.ENTITY_ID_1;
import static com.bank.history.util.TestData.ENTITY_ID_10;
import static com.bank.history.util.TestData.TRANSFER_AUDIT_ID;
import static com.bank.history.util.TestData.TRANSFER_AUDIT_ID_2;
import static com.bank.history.util.TestData.PROFILE_AUDIT_ID;
import static com.bank.history.util.TestData.PROFILE_AUDIT_ID_2;
import static com.bank.history.util.TestData.ACCOUNT_AUDIT_ID;
import static com.bank.history.util.TestData.ACCOUNT_AUDIT_ID_2;
import static com.bank.history.util.TestData.ANTI_FRAUD_AUDIT_ID;
import static com.bank.history.util.TestData.ANTI_FRAUD_AUDIT_ID_2;
import static com.bank.history.util.TestData.PUBLIC_BANK_INFO_AUDIT_ID;
import static com.bank.history.util.TestData.PUBLIC_BANK_INFO_AUDIT_ID_2;
import static com.bank.history.util.TestData.AUTH_AUDIT_ID;
import static com.bank.history.util.TestData.AUTH_AUDIT_ID_2;
import static com.bank.history.util.TestData.SINGLE_ENTITY_ID;

import java.util.List;

public class HistoryMapperTest {

    private final HistoryMapper mapper = Mappers.getMapper(HistoryMapper.class);

    @Test
    void toDto_AllFields() {
        // Создаем сущность с тестовыми значениями из TestData
        HistoryEntity entity = HistoryEntity.builder()
                .id(ENTITY_ID_1)
                .transferAuditId(TRANSFER_AUDIT_ID)
                .profileAuditId(PROFILE_AUDIT_ID)
                .accountAuditId(ACCOUNT_AUDIT_ID)
                .antiFraudAuditId(ANTI_FRAUD_AUDIT_ID)
                .publicBankInfoAuditId(PUBLIC_BANK_INFO_AUDIT_ID)
                .authorizationAuditId(AUTH_AUDIT_ID)
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
    void toEntity_AllFields() {
        // Создаем dto с тестовыми значениями из TestData
        HistoryDto dto = HistoryDto.builder()
                .id(ENTITY_ID_10)
                .transferAuditId(TRANSFER_AUDIT_ID_2)
                .profileAuditId(PROFILE_AUDIT_ID_2)
                .accountAuditId(ACCOUNT_AUDIT_ID_2)
                .antiFraudAuditId(ANTI_FRAUD_AUDIT_ID_2)
                .publicBankInfoAuditId(PUBLIC_BANK_INFO_AUDIT_ID_2)
                .authorizationAuditId(AUTH_AUDIT_ID_2)
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
    void toDtoList_ListMapping() {

        HistoryEntity entity1 = HistoryEntity.builder()
                .id(ENTITY_ID_1)
                .authorizationAuditId(AUTH_AUDIT_ID)
                .build();

        HistoryEntity entity2 = HistoryEntity.builder()
                .id(ENTITY_ID_10)
                .publicBankInfoAuditId(PUBLIC_BANK_INFO_AUDIT_ID_2)
                .build();

        var dtoList = mapper.toDtoList(List.of(entity1, entity2));

        Assertions.assertEquals(2, dtoList.size());
        Assertions.assertEquals(entity1.getId(), dtoList.get(0).getId());
        Assertions.assertEquals(entity1.getAuthorizationAuditId(), dtoList.get(0).getAuthorizationAuditId());
        Assertions.assertEquals(entity2.getId(), dtoList.get(1).getId());
        Assertions.assertEquals(entity2.getPublicBankInfoAuditId(), dtoList.get(1).getPublicBankInfoAuditId());
    }

    @Test
    void toDtoList_SingleEntity() {

        HistoryEntity entity = HistoryEntity.builder().id(SINGLE_ENTITY_ID).build();
        var dtoList = mapper.toDtoList(List.of(entity));
        Assertions.assertEquals(1, dtoList.size());
        Assertions.assertEquals(SINGLE_ENTITY_ID, dtoList.get(0).getId());
    }

    @Test
    void toDto_NullInput() {
        Assertions.assertNull(mapper.toDto(null));
    }

    @Test
    void toEntity_NullInput() {
        Assertions.assertNull(mapper.toEntity(null));
    }
}
