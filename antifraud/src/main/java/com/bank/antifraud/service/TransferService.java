package com.bank.antifraud.service;

import com.bank.antifraud.dto.AccountTransferDto;
import com.bank.antifraud.dto.CardTransferDto;
import com.bank.antifraud.dto.PhoneTransferDto;
import org.springframework.stereotype.Service;

@Service
public interface TransferService {

    void process(AccountTransferDto accountTransferDto);

    void process(CardTransferDto cardTransferDto);

    void process(PhoneTransferDto phoneTransferDto);
}
