package com.bank.antifraud.service;

import com.bank.antifraud.dto.AccountTransferDto;
import com.bank.antifraud.dto.CardTransferDto;
import com.bank.antifraud.dto.PhoneTransferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final SuspiciousTransferService suspiciousTransferService;

    @Value("${transfer.suspicious-threshold}")
    private int suspiciousThreshold;

    @Override
    public void process(AccountTransferDto accountTransferDto) {

        if (accountTransferDto.getAmount() > suspiciousThreshold) {
            suspiciousTransferService.create(accountTransferDto, true, true,
                    ">" + suspiciousThreshold, ">" + suspiciousThreshold);

        } else if (accountTransferDto.getAmount() == suspiciousThreshold) {
            suspiciousTransferService.create(accountTransferDto, false, true,
                    null, "== " + suspiciousThreshold);
        }
    }

    @Override
    public void process(CardTransferDto cardTransferDto) {

        if (cardTransferDto.getAmount() > suspiciousThreshold) {
            suspiciousTransferService.create(cardTransferDto, true, true,
                    ">" + suspiciousThreshold, ">" + suspiciousThreshold);

        } else if (cardTransferDto.getAmount() == suspiciousThreshold) {
            suspiciousTransferService.create(cardTransferDto, false, true,
                    null, "== " + suspiciousThreshold);
        }
    }

    @Override
    public void process(PhoneTransferDto phoneTransferDto) {

        if (phoneTransferDto.getAmount() > suspiciousThreshold) {
            suspiciousTransferService.create(phoneTransferDto, true, true,
                    ">" + suspiciousThreshold, ">" + suspiciousThreshold);

        } else if (phoneTransferDto.getAmount() == suspiciousThreshold) {
            suspiciousTransferService.create(phoneTransferDto, false, true,
                    null, "== " + suspiciousThreshold);
        }
    }
}
