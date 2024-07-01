package com.hexagonal.account.domain.models.valueObjects;

import com.hexagonal.account.domain.models.ErrorOr;

public class Balance {
    private double balance;

    public Balance(double balance) {
        this.balance = balance;
    }

    public double getValue() {
        return balance;
    }

    public void addToBalance(double amount) {
        this.balance += amount;
    }

    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }

    public ErrorOr<Boolean, RuntimeException> balanceCanBeNegative() {
        if (balance < 0) {
            return ErrorOr.failure(new RuntimeException("Saldo no Disponible"));
        }

        return ErrorOr.success(true);
    }
}
