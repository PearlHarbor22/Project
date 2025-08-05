package com.bank.profile.enums;

public enum OperationType {
    CREATE,
    UPDATE;

    public static OperationType fromMethodName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Method name is null");
        }

        if (name.startsWith("create") || name.startsWith("save")) return CREATE;
        if (name.startsWith("update")) return UPDATE;

        throw new UnsupportedOperationException("Неизвестный тип операции: " + name);
    }
}
