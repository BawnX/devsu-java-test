package com.hexagonal.account.application.useCases.account;

import com.hexagonal.account.domain.models.Account;
import com.hexagonal.account.domain.models.ErrorOr;
import com.hexagonal.account.domain.ports.in.account.CreateAccountUseCase;
import com.hexagonal.account.domain.ports.out.AccountRepositoryPort;

public class CreateAccountUseCaseImpl implements CreateAccountUseCase {
    private final AccountRepositoryPort accountRepository;

    public CreateAccountUseCaseImpl(AccountRepositoryPort accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public ErrorOr<Account, RuntimeException> CreateAccount(Account account) {
        try {
            String typeAccount = account.getTypeAccount().getEnum().getValue();
            boolean exist = accountRepository
                    .existsTypeAccountByClientId(
                            typeAccount,
                            account.getClientId().getGuid());

            if (exist) {
                return ErrorOr.failure(
                        new RuntimeException("No se puede tener mas de un mismo tipo de cuenta: " + typeAccount));
            }

            Account savedAccount = accountRepository.save(account);
            return ErrorOr.success(savedAccount);
        } catch (Exception e) {
            return ErrorOr.failure(new RuntimeException("Error al crear la cuenta: " + e.getMessage()));
        }
    }

}
