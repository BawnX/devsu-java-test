package com.hexagonal.account.domain.models;

import java.time.LocalDateTime;

import com.hexagonal.account.domain.models.enums.TypeTransactionsEnum;
import com.hexagonal.account.domain.models.valueObjects.Amount;
import com.hexagonal.account.domain.models.valueObjects.Balance;
import com.hexagonal.account.domain.models.valueObjects.Guid;
import com.hexagonal.account.domain.models.valueObjects.TypeTransaction;

import io.micrometer.common.lang.Nullable;

public class Transaction {
    private final Guid id;
    private final Guid accountId;
    private final Amount amount;
    private final Balance balance;
    private LocalDateTime transactionDateTime;
    private TypeTransaction typeTransaction;

    public Transaction(Guid id,
            Guid accountId,
            Amount amount,
            Balance balance,
            LocalDateTime transactionDateTime,
            TypeTransaction typeTransaction) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.balance = balance;
        this.transactionDateTime = transactionDateTime;
        this.typeTransaction = typeTransaction;
    }

    public static Transaction create(
            @Nullable String id,
            @Nullable String accountId,
            @Nullable int amount,
            @Nullable int balance,
            @Nullable LocalDateTime transactionDateTime,
            @Nullable String typeTransaction) {
        Guid newId = id != null ? new Guid(id) : Guid.Create();
        Guid newAccountId = accountId != null ? new Guid(accountId) : Guid.Create();
        Amount newAmount = amount != 0 ? new Amount(amount) : new Amount(0);
        Balance newBalance = balance != 0 ? new Balance(balance) : new Balance(0);
        LocalDateTime newTransactionDateTime = transactionDateTime != null ? transactionDateTime : LocalDateTime.now();
        TypeTransaction newTypeTransaction = typeTransaction != null ? TypeTransaction.Create(typeTransaction)
                : new TypeTransaction(TypeTransactionsEnum.INVALID);

        return new Transaction(newId, newAccountId, newAmount, newBalance, newTransactionDateTime, newTypeTransaction);
    }

    public void normalizeAmount() {
        double normalizeAmount = Math.abs(amount.getValue());
        switch (typeTransaction.getEnum()) {
            case DEPOSIT -> setAmount(normalizeAmount);
            case WITHDRAWAL -> setAmount(normalizeAmount * -1);
            case INVALID -> setAmount(0);
        }
    }

    public ErrorOr<Double, RuntimeException> rollbackTransaction() {
        try {
            return ErrorOr.success(amount.getValue() * -1);
        } catch (Exception e) {
            return ErrorOr.failure(new RuntimeException("No se convertir la cantidad de transaccion"));
        }
    }

    public void DifferenceAmount(Double otherAmount) {
        setAmount(amount.getValue() + otherAmount);
    }

    public ErrorOr<Transaction, RuntimeException> merge(Transaction updateTransaction) {
        try {
            if (updateTransaction.getAmount().getValue() != 0
                    && updateTransaction.getAmount().getValue() != amount.getValue()) {
                updateTransaction.normalizeAmount();
                setAmount(updateTransaction.getAmount().getValue());
            }

            if (updateTransaction.getBalance().getValue() != 0
                    && updateTransaction.getBalance().getValue() != amount.getValue()) {
                ErrorOr<Boolean, RuntimeException> balanceErrorOr = updateTransaction.getBalance()
                        .balanceCanBeNegative();
                if (balanceErrorOr.isFailure()) {
                    return ErrorOr.failure(new RuntimeException(balanceErrorOr.getError()));
                }

                setBalance(updateTransaction.getBalance().getValue());
            }

            if (!updateTransaction.getTypeTransaction().getEnum().getValue()
                    .equals(typeTransaction.getEnum().getValue())) {
                setTypeTransaction(updateTransaction.getTypeTransaction());
            }
            return ErrorOr.success(this);
        } catch (Exception e) {
            return ErrorOr.failure(new RuntimeException("No se puede actualizar la transaccion: " + e.getMessage()));
        }
    }

    public Guid getId() {
        return id;
    }

    public Amount getAmount() {
        return amount;
    }

    public Balance getBalance() {
        return balance;
    }

    public LocalDateTime getTransactionDateTime() {
        return transactionDateTime;
    }

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public Guid getAccountId() {
        return accountId;
    }

    public void setAmount(double amount) {
        this.amount.setAmount(amount);
    }

    public void setBalance(double balance) {
        this.balance.addToBalance(balance);
    }

    public void setTransactionDateTime(LocalDateTime transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public void setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }
}
