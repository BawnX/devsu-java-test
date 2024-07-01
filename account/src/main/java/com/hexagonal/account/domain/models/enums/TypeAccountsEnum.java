package com.hexagonal.account.domain.models.enums;

public enum TypeAccountsEnum {
    DEBIT("debito"),
    CREDIT("credito"),
    CHECKING("corriente");

    private final String value;

    TypeAccountsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
