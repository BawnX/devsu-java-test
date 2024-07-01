package com.hexagonal.account.infrastructure.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.hexagonal.account.domain.models.Transaction;
import com.hexagonal.account.domain.ports.out.TransactionRepositoryPort;
import com.hexagonal.account.infrastructure.entities.TransactionEntity;
import com.hexagonal.account.infrastructure.jpa.JpaTransactionRepository;

@Repository
public class TransactionRepository implements TransactionRepositoryPort {
    private final JpaTransactionRepository jpaRepository;

    public TransactionRepository(JpaTransactionRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = TransactionEntity.fromDomain(transaction);
        return jpaRepository.save(entity).toDomain();
    }

    @Override
    public Optional<Transaction> findByTransactionId(String transactionId) {
        UUID uuid = UUID.fromString(transactionId);
        return jpaRepository.findById(uuid).map(mapper -> mapper.toDomain());
    }

    @Override
    public List<Transaction> findAll() {
        return jpaRepository.findAll().stream().map(mapper -> mapper.toDomain()).toList();
    }

    @Override
    public boolean existsTransaction(String transactionId) {
        UUID uuid = UUID.fromString(transactionId);
        return jpaRepository.existsById(uuid);
    }

    @Override
    public Optional<Transaction> update(Transaction updateTransaction) {
        TransactionEntity entity = TransactionEntity.fromDomain(updateTransaction);
        return Optional.of(jpaRepository.save(entity).toDomain());
    }

    @Override
    public Optional<Transaction> partialUpdate(Transaction partialTransaction) {
        TransactionEntity entity = TransactionEntity.fromDomain(partialTransaction);
        return Optional.of(jpaRepository.save(entity).toDomain());
    }

    @Override
    public boolean deleteById(String transactionId) {
        UUID uuid = UUID.fromString(transactionId);
        jpaRepository.deleteById(uuid);
        return true;
    }

    @Override
    public List<Transaction> findByAccountId(String accountId) {
        UUID uuid = UUID.fromString(accountId);
        return jpaRepository.findByAccountId(uuid).stream().map(mapper -> mapper.toDomain()).toList();
    }
}
