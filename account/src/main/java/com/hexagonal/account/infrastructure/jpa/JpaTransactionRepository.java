package com.hexagonal.account.infrastructure.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hexagonal.account.infrastructure.entities.TransactionEntity;

public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    @Query(value = "SELECT * FROM accounts.transaction WHERE account_id = :account_id", nativeQuery = true)
    List<TransactionEntity> findByAccountId(@Param("account_id") UUID account_id);
}
