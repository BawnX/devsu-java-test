package com.hexagonal.account.application.useCases.account;

import java.util.List;

import com.hexagonal.account.domain.models.Account;
import com.hexagonal.account.domain.models.ErrorOr;
import com.hexagonal.account.domain.ports.in.account.RetrieveAccountUseCase;
import com.hexagonal.account.domain.ports.out.AccountRepositoryPort;

public class RetrieveAccountUseCaseImpl implements RetrieveAccountUseCase {
    private final AccountRepositoryPort accountRepository;

    public RetrieveAccountUseCaseImpl(AccountRepositoryPort accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public ErrorOr<List<Account>, RuntimeException> GetAccountByClientId(String clientId) {
        try {
            List<Account> accounts = accountRepository.findByClientId(clientId);
            return ErrorOr.success(accounts);
        } catch (Exception e) {
            return ErrorOr
                    .failure(new RuntimeException("Se fallo al obtener las cuentas del cliente: " + e.getMessage()));
        }
    }

    @Override
    public ErrorOr<List<Account>, RuntimeException> GetAllAccounts() {
        try {
            List<Account> accounts = accountRepository.findAll();
            return ErrorOr.success(accounts);
        } catch (Exception e) {
            return ErrorOr.failure(new RuntimeException("Se fallo al obtener todas las cuentas: " + e.getMessage()));
        }
    }

}
