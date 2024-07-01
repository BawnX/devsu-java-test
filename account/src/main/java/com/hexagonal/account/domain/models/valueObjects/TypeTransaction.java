package com.hexagonal.account.domain.models.valueObjects;

import com.hexagonal.account.domain.models.enums.TypeTransactionsEnum;

public class TypeTransaction {
    private final TypeTransactionsEnum type;

    public TypeTransaction(TypeTransactionsEnum typeTransaction) {
        this.type = typeTransaction;
    }

    public static TypeTransaction Create(String typeTransaction) {
        if (typeTransaction == null) {
            return new TypeTransaction(TypeTransactionsEnum.INVALID);
        }

        for (TypeTransactionsEnum transactionsEnum : TypeTransactionsEnum.values()) {
            if (transactionsEnum.getValue().equalsIgnoreCase(typeTransaction)) {
                return new TypeTransaction(transactionsEnum);
            }
        }

        return new TypeTransaction(TypeTransactionsEnum.INVALID);
    }

    public TypeTransactionsEnum getEnum() {
        return type;
    }
}
