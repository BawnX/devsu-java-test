package com.hexagonal.account.infrastructure.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.hexagonal.account.domain.models.Account;
import com.hexagonal.account.domain.models.valueObjects.Balance;
import com.hexagonal.account.domain.models.valueObjects.Guid;
import com.hexagonal.account.domain.models.valueObjects.State;
import com.hexagonal.account.domain.models.valueObjects.TypeAccount;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "account", schema = "accounts")
public class AccountEntity {
    @Id
    @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "client_id", columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID clientId;

    @Column(name = "balance", nullable = false, precision = 10)
    private Double balance;

    @Column(name = "state", nullable = false)
    private Boolean state;

    @Column(name = "type_account", length = 15, nullable = false)
    private String typeAccount;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionEntity> transactions;

    public AccountEntity() {
    }

    public AccountEntity(Double balance,
            UUID clientId,
            UUID id,
            Boolean state,
            List<TransactionEntity> transactions,
            String typeAccount) {
        this.balance = balance;
        this.clientId = clientId;
        this.id = id;
        this.state = state;
        this.transactions = transactions;
        this.typeAccount = typeAccount;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public Double getBalance() {
        return balance;
    }

    public Boolean getState() {
        return state;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    public List<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionEntity> transactions) {
        this.transactions = transactions;
    }

    public static AccountEntity fromDomainModel(Account account) {
        if (account.getTransactions() == null) {
            account.setTransactions(List.of());
        }

        return new AccountEntity(
                account.getBalance().getValue(),
                account.getClientId().getUUID(),
                account.getId().getUUID(),
                account.getState().getValue(),
                account.getTransactions().stream().map(mapper -> TransactionEntity.fromDomain(mapper)).toList(),
                account.getTypeAccount().getEnum().getValue());
    }

    public Account toDomain() {
        return new Account(
                new Guid(getId().toString()),
                new Guid(getClientId().toString()),
                new Balance(getBalance()),
                new State(getState()),
                TypeAccount.Create(getTypeAccount()),
                getTransactions().stream().map(mapper -> mapper.toDomain()).toList());
    }
}
