package com.hexagonal.account.application.useCases.account;

import com.hexagonal.account.domain.models.ErrorOr;
import com.hexagonal.account.domain.ports.in.account.DeleteAccountUseCase;
import com.hexagonal.account.domain.ports.out.AccountRepositoryPort;

public class DeleteAccountUseCaseImpl implements DeleteAccountUseCase {
    private final AccountRepositoryPort accountRepository;

    public DeleteAccountUseCaseImpl(AccountRepositoryPort accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public ErrorOr<Boolean, RuntimeException> DeleteAccount(String accountId) {
        try {
            boolean exists = accountRepository.existsAccount(accountId);
            if (!exists) {
                return ErrorOr.failure(new RuntimeException("No se puede borrar una cuenta si esta no existe"));
            }

            accountRepository.deleteById(accountId);
            return ErrorOr.success(true);
        } catch (Exception e) {
            return ErrorOr.failure(new RuntimeException("No se pudo borrar la cuenta: " + e.getMessage()));
        }
    }

}
