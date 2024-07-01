package com.hexagonal.account.domain.models;

import java.util.List;

import com.hexagonal.account.domain.models.enums.TypeAccountsEnum;
import com.hexagonal.account.domain.models.valueObjects.Balance;
import com.hexagonal.account.domain.models.valueObjects.Guid;
import com.hexagonal.account.domain.models.valueObjects.State;
import com.hexagonal.account.domain.models.valueObjects.TypeAccount;

import io.micrometer.common.lang.Nullable;

public class Account {
    private Guid id;
    private Guid clientId;
    private List<Transaction> transactions;
    private Balance balance;
    private State state;
    private TypeAccount type;

    public Account(
            Guid id,
            Guid clientId,
            Balance balance,
            State state,
            TypeAccount type,
            List<Transaction> transactions) {
        this.id = id;
        this.clientId = clientId;
        this.balance = balance;
        this.state = state;
        this.type = type;
        this.transactions = transactions;
    }

    public static ErrorOr<Account, RuntimeException> create(
            @Nullable String id,
            @Nullable String clientId,
            @Nullable Double balance,
            @Nullable Boolean state,
            @Nullable String type,
            @Nullable List<Transaction> transactions) {
        try {
            Guid guidId = id == null ? Guid.Create() : new Guid(id);
            Guid guidClientId = clientId == null ? Guid.Create() : new Guid(clientId);
            Balance newBalance = balance == null ? new Balance(0) : new Balance(balance);
            State newState = state == null ? new State(true) : new State(state);
            TypeAccount newType = type == null ? new TypeAccount(TypeAccountsEnum.DEBIT)
                    : TypeAccount.Create(type);
            List<Transaction> newTransactions = transactions == null ? List.of() : transactions;

            if (balance != null && balance < 0) {
                throw newBalance.balanceCanBeNegative().getError();
            }

            return ErrorOr.success(new Account(guidId, guidClientId, newBalance, newState, newType, newTransactions));
        } catch (RuntimeException e) {
            return ErrorOr.failure(new RuntimeException("Error en los datos: " + e.getMessage()));
        }
    }

    public ErrorOr<Account, RuntimeException> merge(Account updateAccount) {
        try {

            if (updateAccount.getBalance().getValue() != 0
                    && updateAccount.getBalance().getValue() != getBalance().getValue()) {
                ErrorOr<Boolean, RuntimeException> balanceErrorOr = updateAccount.getBalance().balanceCanBeNegative();

                if (balanceErrorOr.isFailure()) {
                    return ErrorOr.failure(balanceErrorOr.getError());
                }
                setBalance(getBalance());
            }

            if (updateAccount.getState().getValue() != state.getValue()) {
                setState(updateAccount.getState());
            }

            if (updateAccount.getTypeAccount().getEnum().getValue() != null &&
                    !getTypeAccount().getEnum().getValue()
                            .equals(updateAccount.getTypeAccount().getEnum().getValue())) {
                setType(updateAccount.getTypeAccount());
            }

            return ErrorOr.success(this);
        } catch (Exception e) {
            return ErrorOr.failure(new RuntimeException());
        }
    }

    public Guid getId() {
        return id;
    }

    public Guid getClientId() {
        return clientId;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Balance getBalance() {
        return balance;
    }

    public State getState() {
        return state;
    }

    public TypeAccount getTypeAccount() {
        return type;
    }

    public void setId(Guid id) {
        this.id = id;
    }

    public void addToBalance(Double amount) {
        this.balance.addToBalance(amount);
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setType(TypeAccount type) {
        this.type = type;
    }

    public TypeAccount getType() {
        return type;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setClientId(Guid clientId) {
        this.clientId = clientId;
    }

}
