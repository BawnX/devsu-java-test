package com.hexagonal.account.domain.models.valueObjects;

public class Amount {
    private double amount;

    public Amount(double amount) {
        this.amount = amount;
    }

    public double getValue() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
