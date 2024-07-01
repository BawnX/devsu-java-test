package com.hexagonal.account.application.useCases.account;

import java.util.Optional;

import com.hexagonal.account.domain.models.Account;
import com.hexagonal.account.domain.models.ErrorOr;
import com.hexagonal.account.domain.models.valueObjects.Guid;
import com.hexagonal.account.domain.ports.in.account.UpdateAccountUseCase;
import com.hexagonal.account.domain.ports.out.AccountRepositoryPort;

public class UpdateAccountUseCaseImpl implements UpdateAccountUseCase {
    private final AccountRepositoryPort accountRepository;

    public UpdateAccountUseCaseImpl(AccountRepositoryPort accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public ErrorOr<Optional<Account>, RuntimeException> UpdateAccount(String id, Account updateAccount) {
        try {
            ErrorOr<Boolean, RuntimeException> validId = Guid.isValid(id);

            if (validId.isFailure()) {
                return ErrorOr.failure(new RuntimeException(validId.getError()));
            }

            Optional<Account> exists = accountRepository.findByAccountId(id);
            if (!exists.isPresent()) {
                return ErrorOr.failure(new RuntimeException("No se puede actualizar una cuenta que no existe"));
            }

            updateAccount.setClientId(exists.get().getClientId());
            updateAccount.setTransactions(exists.get().getTransactions());

            Optional<Account> updatedAccount = accountRepository.update(updateAccount);
            return ErrorOr.success(updatedAccount);
        } catch (

        Exception e) {
            return ErrorOr.failure(new RuntimeException("Error al actualizar la cuenta: " + e.getMessage()));
        }
    }

    @Override
    public ErrorOr<Optional<Account>, RuntimeException> PartialUpdateAccount(String id,
            Optional<Account> partialAccount) {
        try {
            ErrorOr<Boolean, RuntimeException> validId = Guid.isValid(id);
            if (validId.isFailure()) {
                return ErrorOr.failure(new RuntimeException(validId.getError()));
            }

            Optional<Account> originalAccount = accountRepository.findByAccountId(id);
            if (!originalAccount.isPresent()) {
                return ErrorOr.failure(new RuntimeException("No se puede actualizar una cuenta que no existe"));
            }

            Account updateAccount = partialAccount
                    .map(account -> {
                        ErrorOr<Account, RuntimeException> errorOr = originalAccount.get().merge(account);
                        if (errorOr.isFailure()) {
                            throw errorOr.getError();
                        }
                        return errorOr.getValue();
                    })
                    .orElseGet(originalAccount::get);

            Optional<Account> updatedAccount = accountRepository.partialUpdate(updateAccount);
            return ErrorOr.success(updatedAccount);
        } catch (Exception e) {
            return ErrorOr.failure(new RuntimeException("Error al actualizar la cuenta: " + e.getMessage()));
        }
    }

}
