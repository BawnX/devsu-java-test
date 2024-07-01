package com.hexagonal.account.application.useCases.transactions;

import java.util.Optional;

import com.hexagonal.account.domain.models.ErrorOr;
import com.hexagonal.account.domain.models.Transaction;
import com.hexagonal.account.domain.models.valueObjects.Guid;
import com.hexagonal.account.domain.ports.in.transaction.UpdateTransactionUseCase;
import com.hexagonal.account.domain.ports.out.TransactionRepositoryPort;

public class UpdateTransactionUseCaseImpl implements UpdateTransactionUseCase {
    private final TransactionRepositoryPort transactionRepositoryPort;

    public UpdateTransactionUseCaseImpl(TransactionRepositoryPort transactionRepositoryPort) {
        this.transactionRepositoryPort = transactionRepositoryPort;
    }

    @Override
    public ErrorOr<Optional<Transaction>, RuntimeException> UpdateTransaction(String transactionId,
            Transaction updateTransaction) {
        try {
            ErrorOr<Boolean, RuntimeException> validId = Guid.isValid(transactionId);
            if (validId.isFailure()) {
                return ErrorOr.failure(validId.getError());
            }

            Optional<Transaction> transactionOriginal = transactionRepositoryPort.findByTransactionId(transactionId);
            if (!transactionOriginal.isPresent()) {
                return ErrorOr.failure(new RuntimeException("No se pudo obtener la transaccion"));
            }

            updateTransaction.normalizeAmount();
            return ErrorOr.success(transactionRepositoryPort.update(updateTransaction));
        } catch (Exception e) {
            return ErrorOr.failure(new RuntimeException("No se pudo actualizar la transacion: " + e.getMessage()));
        }
    }

    @Override
    public ErrorOr<Optional<Transaction>, RuntimeException> PartialUpdateTransaction(String transactionId,
            Optional<Transaction> partialTransaction) {
        try {
            ErrorOr<Boolean, RuntimeException> validId = Guid.isValid(transactionId);
            if (validId.isFailure()) {
                return ErrorOr.failure(validId.getError());
            }

            Optional<Transaction> transactionOriginal = transactionRepositoryPort.findByTransactionId(transactionId);
            if (!transactionOriginal.isPresent()) {
                return ErrorOr.failure(new RuntimeException("No se pudo obtener la transaccion"));
            }

            Transaction newPartialTransaction = partialTransaction
                    .map((Transaction pc) -> {
                        ErrorOr<Transaction, RuntimeException> errorOr = transactionOriginal.get().merge(pc);
                        if (errorOr.isFailure()) {
                            throw errorOr.getError();
                        }
                        return errorOr.getValue();
                    })
                    .orElseGet(transactionOriginal::get);
            return ErrorOr.success(transactionRepositoryPort.partialUpdate(newPartialTransaction));
        } catch (Exception e) {
            return ErrorOr.failure(new RuntimeException("No se pudo actualizar la transaccion: " + e.getMessage()));
        }
    }

}
