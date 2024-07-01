package com.hexagonal.account.application.useCases.transactions;

import java.util.Optional;

import com.hexagonal.account.domain.models.Account;
import com.hexagonal.account.domain.models.ErrorOr;
import com.hexagonal.account.domain.models.Transaction;
import com.hexagonal.account.domain.models.valueObjects.Guid;
import com.hexagonal.account.domain.ports.in.transaction.DeleteTransactionUseCase;
import com.hexagonal.account.domain.ports.out.AccountRepositoryPort;
import com.hexagonal.account.domain.ports.out.TransactionRepositoryPort;

public class DeleteTransactionUseCaseImpl implements DeleteTransactionUseCase {
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final AccountRepositoryPort accountRepositoryPort;

    public DeleteTransactionUseCaseImpl(TransactionRepositoryPort transactionRepositoryPort,
            AccountRepositoryPort accountRepositoryPort) {
        this.transactionRepositoryPort = transactionRepositoryPort;
        this.accountRepositoryPort = accountRepositoryPort;
    }

    @Override
    public ErrorOr<Boolean, RuntimeException> DeleteTransaction(String transactionId) {
        try {
            ErrorOr<Boolean, RuntimeException> validId = Guid.isValid(transactionId);
            if (validId.isFailure()) {
                return ErrorOr.failure(validId.getError());
            }

            Optional<Transaction> transaction = transactionRepositoryPort.findByTransactionId(transactionId);
            if (!transaction.isPresent()) {
                return ErrorOr.failure(new RuntimeException("No se puede eliminar una transacción que no existe"));
            }

            ErrorOr<Double, RuntimeException> amountToRollback = transaction.get().rollbackTransaction();

            if (amountToRollback.isFailure()) {
                return ErrorOr.failure(amountToRollback.getError());
            }

            Optional<Account> account = accountRepositoryPort
                    .findByAccountId(transaction.get().getAccountId().getGuid());

            if (!account.isPresent()) {
                return ErrorOr.failure(new RuntimeException(
                        "No se puede eliminar una transacción que no existe en el registro de cuentas"));
            }

            account.get().addToBalance(amountToRollback.getValue());

            if (account.get().getBalance().balanceCanBeNegative().isFailure()) {
                return ErrorOr.failure(account.get().getBalance().balanceCanBeNegative().getError());
            }

            accountRepositoryPort.save(account.get());
            transactionRepositoryPort.deleteById(transactionId);
            return ErrorOr.success(true);
        } catch (Exception e) {
            return ErrorOr.failure(new RuntimeException("No se pudo eliminar la transacion: " + e.getMessage()));
        }
    }

}
