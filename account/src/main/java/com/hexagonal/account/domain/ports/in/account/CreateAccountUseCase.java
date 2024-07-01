package com.hexagonal.account.domain.ports.in.account;

import com.hexagonal.account.domain.models.Account;
import com.hexagonal.account.domain.models.ErrorOr;

public interface CreateAccountUseCase {
    ErrorOr<Account, RuntimeException> CreateAccount(Account account);
}
