package com.bank.antifraud.util;

import com.bank.antifraud.dto.AccountTransferDto;
import com.bank.antifraud.dto.CardTransferDto;
import com.bank.antifraud.dto.PhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransfer;
import com.bank.antifraud.entity.SuspiciousCardTransfer;
import com.bank.antifraud.entity.SuspiciousPhoneTransfer;

public class ValidEntitiesCreator {

    public static SuspiciousAccountTransfer getSuspiciousAccountTransfer(Long testTransferId, String testBlockedReason, String testSuspiciousReason) {
        return SuspiciousAccountTransfer.builder()
                .accountTransferId(testTransferId)
                .isBlocked(true)
                .isSuspicious(true)
                .blockedReason(testBlockedReason)
                .suspiciousReason(testSuspiciousReason)
                .build();
    }

    public static SuspiciousPhoneTransfer getSuspiciousPhoneTransfer(Long testTransferId, String testBlockedReason, String testSuspiciousReason) {
        return SuspiciousPhoneTransfer.builder()
                .phoneTransferId(testTransferId)
                .isBlocked(true)
                .isSuspicious(true)
                .blockedReason(testBlockedReason)
                .suspiciousReason(testSuspiciousReason)
                .build();
    }

    public static SuspiciousCardTransfer getSuspiciousCardTransfer(Long testTransferId, String testBlockedReason, String testSuspiciousReason) {
        return SuspiciousCardTransfer.builder()
                .cardTransferId(testTransferId)
                .isBlocked(true)
                .isSuspicious(true)
                .blockedReason(testBlockedReason)
                .suspiciousReason(testSuspiciousReason)
                .build();
    }

    public static AccountTransferDto createAccountTransferDto(Long id, Double amount) {
        return AccountTransferDto.builder()
                .id(id)
                .amount(amount)
                .build();
    }

    public static CardTransferDto createCardTransferDto(Long id, Double amount) {
        return CardTransferDto.builder()
                .id(id)
                .amount(amount)
                .build();
    }

    public static PhoneTransferDto createPhoneTransferDto(Long id, Double amount) {
        return PhoneTransferDto.builder()
                .id(id)
                .amount(amount)
                .build();
    }
}
