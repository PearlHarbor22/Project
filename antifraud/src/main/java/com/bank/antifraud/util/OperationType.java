package com.bank.antifraud.util;

public enum OperationType {
    CREATE,
    UPDATE,
    DELETE;

    public static OperationType resolveByMethodName(String methodName) {
        String lower = methodName.toLowerCase();
        if (lower.contains("create")) {
            return CREATE;
        } else if (lower.contains("update")) {
            return UPDATE;
        } else if (lower.contains("delete")) {
            return DELETE;
        } else {
            return CREATE;
        }
    }
}
