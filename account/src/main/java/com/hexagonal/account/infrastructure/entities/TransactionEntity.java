package com.hexagonal.account.infrastructure.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.hexagonal.account.domain.models.Transaction;
import com.hexagonal.account.domain.models.valueObjects.Amount;
import com.hexagonal.account.domain.models.valueObjects.Balance;
import com.hexagonal.account.domain.models.valueObjects.Guid;
import com.hexagonal.account.domain.models.valueObjects.TypeTransaction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction", schema = "accounts")
public class TransactionEntity {
    @Id
    @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "balance", nullable = false, precision = 10)
    private Double balance;

    @Column(name = "amount", nullable = false, precision = 10)
    private Double amount;

    @Column(name = "type_transaction", length = 15, nullable = false)
    private String typeTransaction;

    @Column(name = "transaction_date_time", nullable = false)
    private LocalDateTime transactionDateTime;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    public TransactionEntity() {
    }

    public TransactionEntity(AccountEntity account, Double amount, Double balance, UUID id, String typeTransaction,
            LocalDateTime transactionDateTime) {
        this.account = account;
        this.amount = amount;
        this.balance = balance;
        this.id = id;
        this.typeTransaction = typeTransaction;
        this.transactionDateTime = transactionDateTime;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public LocalDateTime getTransactionDateTime() {
        return transactionDateTime;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @PrePersist
    public void prePersist() {
        this.transactionDateTime = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public static TransactionEntity fromDomain(Transaction transaction) {
        AccountEntity entity = new AccountEntity();
        entity.setId(UUID.fromString(transaction.getAccountId().getGuid()));

        return new TransactionEntity(entity,
                transaction.getAmount().getValue(),
                transaction.getBalance().getValue(),
                transaction.getId().getUUID(),
                transaction.getTypeTransaction().getEnum().getValue(),
                transaction.getTransactionDateTime());
    }

    public Transaction toDomain() {
        return new Transaction(
                new Guid(getId().toString()),
                new Guid(getAccount().getId().toString()),
                new Amount(getAmount()),
                new Balance(getBalance()),
                getTransactionDateTime(),
                TypeTransaction.Create(getTypeTransaction()));
    }
}
