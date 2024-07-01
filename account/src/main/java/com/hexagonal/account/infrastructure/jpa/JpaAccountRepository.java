package com.hexagonal.account.infrastructure.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hexagonal.account.infrastructure.entities.AccountEntity;

public interface JpaAccountRepository extends JpaRepository<AccountEntity, UUID> {
    @Query(value = "SELECT * FROM accounts.account WHERE client_id = :clientId", nativeQuery = true)
    List<AccountEntity> findByClientId(@Param("clientId") UUID clientId);

    @Query(value = "SELECT COUNT(*) > 0 FROM accounts.account WHERE type_account = :typeAccount AND client_id = :clientId", nativeQuery = true)
    boolean existsByTypeAccountAndClientId(@Param("typeAccount") String typeAccount, @Param("clientId") UUID clientId);
}
