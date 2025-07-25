package com.bank.profile.enums;

public enum OperationType {
    CREATE, UPDATE, DELETE, GET, UNKNOWN;

    public static OperationType fromMethodName(String name) {
        if (name.startsWith("create")) return CREATE;
        if (name.startsWith("update")) return UPDATE;
        if (name.startsWith("delete")) return DELETE;
        if (name.startsWith("save")) return CREATE;
        if (name.startsWith("get")) return GET;
        return UNKNOWN;
    }
}
