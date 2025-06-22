package com.bank.antifraud.service;

import com.bank.antifraud.dto.AccountTransferDto;
import com.bank.antifraud.dto.CardTransferDto;
import com.bank.antifraud.dto.PhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransfer;
import com.bank.antifraud.entity.SuspiciousCardTransfer;
import com.bank.antifraud.entity.SuspiciousPhoneTransfer;
import org.springframework.stereotype.Component;

@Component
public interface SuspiciousTransferService {

    SuspiciousAccountTransfer create(
            AccountTransferDto accountTransferDto,
            boolean isBlocked,
            boolean isSuspicious,
            String blockedReason,
            String suspiciousReason);

    SuspiciousCardTransfer create(
            CardTransferDto cardTransferDto,
            boolean isBlocked,
            boolean isSuspicious,
            String blockedReason,
            String suspiciousReason);

    SuspiciousPhoneTransfer create(
            PhoneTransferDto phoneTransferDto,
            boolean isBlocked,
            boolean isSuspicious,
            String blockedReason,
            String suspiciousReason);
}
