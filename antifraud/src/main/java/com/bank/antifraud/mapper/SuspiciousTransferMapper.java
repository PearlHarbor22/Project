package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransfer;
import com.bank.antifraud.entity.SuspiciousCardTransfer;
import com.bank.antifraud.entity.SuspiciousPhoneTransfer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuspiciousTransferMapper {

    SuspiciousAccountTransferDto toSuspiciousAccountTransferDto(SuspiciousAccountTransfer suspiciousAccountTransfer);

    SuspiciousCardTransferDto toSuspiciousCardTransferDto(SuspiciousCardTransfer suspiciousCardTransfer);

    SuspiciousPhoneTransferDto toSuspiciousPhoneTransferDto(SuspiciousPhoneTransfer suspiciousPhoneTransfer);
}
