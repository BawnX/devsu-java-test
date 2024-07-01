package com.hexagonal.account.application.services;

import java.util.List;
import java.util.Optional;

import com.hexagonal.account.domain.models.Account;
import com.hexagonal.account.domain.models.ErrorOr;
import com.hexagonal.account.domain.ports.in.account.CreateAccountUseCase;
import com.hexagonal.account.domain.ports.in.account.DeleteAccountUseCase;
import com.hexagonal.account.domain.ports.in.account.RetrieveAccountUseCase;
import com.hexagonal.account.domain.ports.in.account.UpdateAccountUseCase;

public class AccountService
        implements RetrieveAccountUseCase, CreateAccountUseCase, UpdateAccountUseCase, DeleteAccountUseCase {
    private final RetrieveAccountUseCase retrieveAccountUseCase;
    private final CreateAccountUseCase createAccountUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;

    public AccountService(CreateAccountUseCase createAccountUseCase, DeleteAccountUseCase deleteAccountUseCase,
            RetrieveAccountUseCase retrieveAccountUseCase, UpdateAccountUseCase updateAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.deleteAccountUseCase = deleteAccountUseCase;
        this.retrieveAccountUseCase = retrieveAccountUseCase;
        this.updateAccountUseCase = updateAccountUseCase;
    }

    @Override
    public ErrorOr<List<Account>, RuntimeException> GetAccountByClientId(String clientId) {
        return retrieveAccountUseCase.GetAccountByClientId(clientId);
    }

    @Override
    public ErrorOr<List<Account>, RuntimeException> GetAllAccounts() {
        return retrieveAccountUseCase.GetAllAccounts();
    }

    @Override
    public ErrorOr<Account, RuntimeException> CreateAccount(Account account) {
        return createAccountUseCase.CreateAccount(account);
    }

    @Override
    public ErrorOr<Optional<Account>, RuntimeException> UpdateAccount(String id, Account updateClient) {
        return updateAccountUseCase.UpdateAccount(id, updateClient);
    }

    @Override
    public ErrorOr<Optional<Account>, RuntimeException> PartialUpdateAccount(String id,
            Optional<Account> partialClient) {
        return updateAccountUseCase.PartialUpdateAccount(id, partialClient);
    }

    @Override
    public ErrorOr<Boolean, RuntimeException> DeleteAccount(String accountId) {
        return deleteAccountUseCase.DeleteAccount(accountId);
    }

}
