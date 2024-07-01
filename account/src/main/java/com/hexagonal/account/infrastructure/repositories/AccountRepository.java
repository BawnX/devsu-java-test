package com.hexagonal.account.infrastructure.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.hexagonal.account.domain.models.Account;
import com.hexagonal.account.domain.ports.out.AccountRepositoryPort;
import com.hexagonal.account.infrastructure.entities.AccountEntity;
import com.hexagonal.account.infrastructure.jpa.JpaAccountRepository;

@Repository
public class AccountRepository implements AccountRepositoryPort {
    private final JpaAccountRepository jpaRepository;

    public AccountRepository(JpaAccountRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Account save(Account account) {
        AccountEntity accountEntity = AccountEntity.fromDomainModel(account);

        return jpaRepository.save(accountEntity).toDomain();
    }

    @Override
    public Optional<Account> findByAccountId(String accountId) {
        try {
            UUID uuid = UUID.fromString(accountId);
            return jpaRepository.findById(uuid).map(entity -> entity.toDomain());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Account> findByClientId(String clientId) {
        UUID uuid = UUID.fromString(clientId);
        return jpaRepository.findByClientId(uuid).stream().map(mapper -> mapper.toDomain()).toList();
    }

    @Override
    public List<Account> findAll() {
        return jpaRepository.findAll().stream().map(mapper -> mapper.toDomain()).toList();
    }

    @Override
    public boolean existsAccount(String accountId) {
        UUID uuid = UUID.fromString(accountId);
        return jpaRepository.existsById(uuid);
    }

    @Override
    public boolean existsTypeAccountByClientId(String typeAccount, String clientId) {
        return jpaRepository.existsByTypeAccountAndClientId(typeAccount, UUID.fromString(clientId));
    }

    @Override
    public Optional<Account> update(Account updateAccount) {
        return Optional.of(jpaRepository.save(AccountEntity.fromDomainModel(updateAccount)).toDomain());
    }

    @Override
    public Optional<Account> partialUpdate(Account partialClient) {
        return Optional.of(jpaRepository.save(AccountEntity.fromDomainModel(partialClient)).toDomain());
    }

    @Override
    public boolean deleteById(String accountId) {
        UUID uuid = UUID.fromString(accountId);
        jpaRepository.deleteById(uuid);
        return true;
    }

}
