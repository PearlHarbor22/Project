package com.bank.history.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
