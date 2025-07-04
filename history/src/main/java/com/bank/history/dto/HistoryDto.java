package com.bank.history.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для передачи информации об изменениях истории.
 * Используется при обмене сообщениями через Kafka.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDto {

    private Long id;
    private Long transferAuditId;
    private Long profileAuditId;
    private Long accountAuditId;
    private Long antiFraudAuditId;
    private Long publicBankInfoAuditId;
    private Long authorizationAuditId;
}
