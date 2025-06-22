package com.bank.antifraud.service;

public interface SuspiciousTransferSaveService<T> {
    void save(T entity);
}
