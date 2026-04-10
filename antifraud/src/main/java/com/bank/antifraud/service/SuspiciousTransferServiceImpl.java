package com.bank.antifraud.service;

import com.bank.antifraud.aspect.Audited;
import com.bank.antifraud.config.KafkaErrorLogger;
import com.bank.antifraud.dto.AccountTransferDto;
import com.bank.antifraud.dto.CardTransferDto;
import com.bank.antifraud.dto.PhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransfer;
import com.bank.antifraud.entity.SuspiciousCardTransfer;
import com.bank.antifraud.entity.SuspiciousPhoneTransfer;
import com.bank.antifraud.kafka.producer.SuspiciousTransferProducer;
import com.bank.antifraud.mapper.SuspiciousTransferMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса для создания подозрительных переводов (счета, карты, телефоны).
 * Сохраняет и отправляет информацию о подозрительных операциях.
 */
@Audited
@Service
@RequiredArgsConstructor
public class SuspiciousTransferServiceImpl implements SuspiciousTransferService {

    private final SuspiciousAccountTransferService suspiciousAccountTransferService;
    private final SuspiciousCardTransferService suspiciousCardTransferService;
    private final SuspiciousPhoneTransferService suspiciousPhoneTransferService;
    private final SuspiciousTransferMapper suspiciousTransferMapper;
    private final SuspiciousTransferProducer suspiciousTransferProducer;
    private final KafkaErrorLogger kafkaErrorLogger;

    /**
     * Создаёт подозрительный перевод между счетами.
     * @param accountTransferDto DTO перевода между счетами
     * @param isBlocked Заблокирован ли перевод
     * @param isSuspicious Является ли перевод подозрительным
     * @param blockedReason Причина блокировки
     * @param suspiciousReason Причина подозрения
     * @return Сущность подозрительного перевода или null при ошибке
     */
    public SuspiciousAccountTransfer create(
            AccountTransferDto accountTransferDto,
            boolean isBlocked,
            boolean isSuspicious,
            String blockedReason,
            String suspiciousReason) {
        try {
            if (accountTransferDto.getId() == null) {
                throw new EntityNotFoundException("AccountTransferDto id не может быть null");
            }
            SuspiciousAccountTransfer suspiciousAccountTransfer = SuspiciousAccountTransfer.builder()
                    .accountTransferId(accountTransferDto.getId())
                    .isBlocked(isBlocked)
                    .isSuspicious(isSuspicious)
                    .blockedReason(blockedReason)
                    .suspiciousReason(suspiciousReason)
                    .build();

            suspiciousAccountTransferService.save(suspiciousAccountTransfer);

            suspiciousTransferProducer.sendSuspiciousTransfer(
                    suspiciousTransferMapper.toSuspiciousAccountTransferDto(suspiciousAccountTransfer));
            return suspiciousAccountTransfer;
        } catch (EntityNotFoundException e) {
            kafkaErrorLogger.handleEntityNotFoundException(e, accountTransferDto);
        } catch (IllegalArgumentException e) {
            kafkaErrorLogger.handleIllegalArgumentException(e, accountTransferDto);
        } catch (RuntimeException e) {
            kafkaErrorLogger.handleRuntimeException(e, accountTransferDto);
        }
        return null;
    }

    /**
     * Создаёт подозрительный перевод по карте.
     * @param cardTransferDto DTO перевода по карте
     * @param isBlocked Заблокирован ли перевод
     * @param isSuspicious Является ли перевод подозрительным
     * @param blockedReason Причина блокировки
     * @param suspiciousReason Причина подозрения
     * @return Сущность подозрительного перевода или null при ошибке
     */
    public SuspiciousCardTransfer create(
            CardTransferDto cardTransferDto,
            boolean isBlocked,
            boolean isSuspicious,
            String blockedReason,
            String suspiciousReason) {
        try {
            if (cardTransferDto.getId() == null) {
                throw new EntityNotFoundException("CardTransferDto id не может быть null");
            }
            SuspiciousCardTransfer suspiciousCardTransfer = SuspiciousCardTransfer.builder()
                    .cardTransferId(cardTransferDto.getId())
                    .isBlocked(isBlocked)
                    .isSuspicious(isSuspicious)
                    .blockedReason(blockedReason)
                    .suspiciousReason(suspiciousReason)
                    .build();

            suspiciousCardTransferService.save(suspiciousCardTransfer);

            suspiciousTransferProducer.sendSuspiciousTransfer(
                    suspiciousTransferMapper.toSuspiciousCardTransferDto(suspiciousCardTransfer));
            return suspiciousCardTransfer;
        } catch (EntityNotFoundException e) {
            kafkaErrorLogger.handleEntityNotFoundException(e, cardTransferDto);
        } catch (IllegalArgumentException e) {
            kafkaErrorLogger.handleIllegalArgumentException(e, cardTransferDto);
        } catch (RuntimeException e) {
            kafkaErrorLogger.handleRuntimeException(e, cardTransferDto);
        }
        return null;
    }

    /**
     * Создаёт подозрительный перевод по номеру телефона.
     * @param phoneTransferDto DTO перевода по номеру телефона
     * @param isBlocked Заблокирован ли перевод
     * @param isSuspicious Является ли перевод подозрительным
     * @param blockedReason Причина блокировки
     * @param suspiciousReason Причина подозрения
     * @return Сущность подозрительного перевода или null при ошибке
     */
    public SuspiciousPhoneTransfer create(
            PhoneTransferDto phoneTransferDto,
            boolean isBlocked,
            boolean isSuspicious,
            String blockedReason,
            String suspiciousReason) {
        try {
            if (phoneTransferDto.getId() == null) {
                throw new EntityNotFoundException("PhoneTransferDto id не может быть null");
            }
            SuspiciousPhoneTransfer suspiciousPhoneTransfer = SuspiciousPhoneTransfer.builder()
                    .phoneTransferId(phoneTransferDto.getId())
                    .isBlocked(isBlocked)
                    .isSuspicious(isSuspicious)
                    .blockedReason(blockedReason)
                    .suspiciousReason(suspiciousReason)
                    .build();

            suspiciousPhoneTransferService.save(suspiciousPhoneTransfer);

            suspiciousTransferProducer.sendSuspiciousTransfer(
                    suspiciousTransferMapper.toSuspiciousPhoneTransferDto(suspiciousPhoneTransfer));
            return suspiciousPhoneTransfer;
        } catch (EntityNotFoundException e) {
            kafkaErrorLogger.handleEntityNotFoundException(e, phoneTransferDto);
        } catch (IllegalArgumentException e) {
            kafkaErrorLogger.handleIllegalArgumentException(e, phoneTransferDto);
        } catch (RuntimeException e) {
            kafkaErrorLogger.handleRuntimeException(e, phoneTransferDto);
        }
        return null;
    }
}
