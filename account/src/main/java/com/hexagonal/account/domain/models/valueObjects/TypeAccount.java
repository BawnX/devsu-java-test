package com.hexagonal.account.domain.models.valueObjects;

import com.hexagonal.account.domain.models.enums.TypeAccountsEnum;

public class TypeAccount {
    private final TypeAccountsEnum type;

    public TypeAccount(TypeAccountsEnum type) {
        this.type = type;
    }

    public static TypeAccount Create(String type) {
        if (type == null) {
            return new TypeAccount(TypeAccountsEnum.DEBIT);
        }

        for (TypeAccountsEnum typeAccountsEnum : TypeAccountsEnum.values()) {
            if (typeAccountsEnum.getValue().equals(type.toLowerCase())) {
                return new TypeAccount(typeAccountsEnum);
            }
        }

        return new TypeAccount(TypeAccountsEnum.DEBIT);
    }

    public TypeAccountsEnum getEnum() {
        return type;
    }
}
