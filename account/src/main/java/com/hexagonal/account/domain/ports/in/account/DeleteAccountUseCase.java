package com.hexagonal.account.domain.ports.in.account;

import com.hexagonal.account.domain.models.ErrorOr;

public interface DeleteAccountUseCase {
    ErrorOr<Boolean, RuntimeException> DeleteAccount(String accountId);
}
