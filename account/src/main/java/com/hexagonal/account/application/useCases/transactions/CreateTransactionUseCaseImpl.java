package com.hexagonal.account.application.useCases.transactions;

import java.util.Optional;

import com.hexagonal.account.application.dtos.CreateMovimientoRequest;
import com.hexagonal.account.application.dtos.MovimientoResponse;
import com.hexagonal.account.domain.models.Account;
import com.hexagonal.account.domain.models.ErrorOr;
import com.hexagonal.account.domain.models.Transaction;
import com.hexagonal.account.domain.ports.in.transaction.CreateTransactionUseCase;
import com.hexagonal.account.domain.ports.out.AccountRepositoryPort;
import com.hexagonal.account.domain.ports.out.TransactionRepositoryPort;

public class CreateTransactionUseCaseImpl implements CreateTransactionUseCase {

    private final TransactionRepositoryPort transactionRepositoryPort;
    private final AccountRepositoryPort accountRepositoryPort;

    public CreateTransactionUseCaseImpl(TransactionRepositoryPort transactionRepositoryPort,
            AccountRepositoryPort accountRepositoryPort) {
        this.transactionRepositoryPort = transactionRepositoryPort;
        this.accountRepositoryPort = accountRepositoryPort;
    }

    @Override
    public ErrorOr<MovimientoResponse, RuntimeException> CreateTransaction(CreateMovimientoRequest movimiento) {
        try {
            Transaction transaction = movimiento.toModel();
            transaction.normalizeAmount();
            Optional<Account> accountFounded = accountRepositoryPort
                    .findByAccountId(transaction.getAccountId().getGuid());

            if (!accountFounded.isPresent()) {
                return ErrorOr.failure(new RuntimeException("No se pudo encontrar la cuenta de la transferencia"));
            }

            Account accountToUpdate = accountFounded.get();
            accountToUpdate.getBalance().addToBalance(transaction.getAmount().getValue());
            ErrorOr<Boolean, RuntimeException> balanceErrorOr = accountToUpdate.getBalance().balanceCanBeNegative();
            if (balanceErrorOr.isFailure()) {
                return ErrorOr.failure(balanceErrorOr.getError());
            }

            transaction.getBalance().setBalance(accountToUpdate.getBalance().getValue());
            transactionRepositoryPort.save(transaction);
            accountRepositoryPort.save(accountToUpdate);

            MovimientoResponse response = MovimientoResponse.fromModel(transaction, accountToUpdate);
            return ErrorOr.success(response);
        } catch (Exception e) {
            return ErrorOr.failure(new RuntimeException("No se pudo crear la transaccion: " + e.getMessage()));
        }
    }

}
