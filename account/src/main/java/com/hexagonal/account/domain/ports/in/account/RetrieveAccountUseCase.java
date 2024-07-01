package com.hexagonal.account.domain.ports.in.account;

import java.util.List;

import com.hexagonal.account.domain.models.Account;
import com.hexagonal.account.domain.models.ErrorOr;

public interface RetrieveAccountUseCase {
    ErrorOr<List<Account>, RuntimeException> GetAccountByClientId(String clientId);

    ErrorOr<List<Account>, RuntimeException> GetAllAccounts();
}
