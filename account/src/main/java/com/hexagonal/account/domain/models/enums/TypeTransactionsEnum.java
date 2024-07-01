package com.hexagonal.account.domain.models.enums;

public enum TypeTransactionsEnum {
    DEPOSIT("deposito"),
    WITHDRAWAL("retiro"),
    INVALID("invalido");

    private final String value;

    TypeTransactionsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
