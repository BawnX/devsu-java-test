package com.hexagonal.account.domain.ports.in.account;

import java.util.Optional;

import com.hexagonal.account.domain.models.Account;
import com.hexagonal.account.domain.models.ErrorOr;

public interface UpdateAccountUseCase {
    ErrorOr<Optional<Account>, RuntimeException> UpdateAccount(String id, Account updateAccount);

    ErrorOr<Optional<Account>, RuntimeException> PartialUpdateAccount(String id, Optional<Account> partialAccount);
}
