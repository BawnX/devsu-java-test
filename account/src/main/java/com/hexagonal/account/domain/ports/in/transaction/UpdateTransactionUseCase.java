package com.hexagonal.account.domain.ports.in.transaction;

import java.util.Optional;

import com.hexagonal.account.domain.models.ErrorOr;
import com.hexagonal.account.domain.models.Transaction;

public interface UpdateTransactionUseCase {
        ErrorOr<Optional<Transaction>, RuntimeException> UpdateTransaction(String transactionId,
                        Transaction updateTransaction);

        ErrorOr<Optional<Transaction>, RuntimeException> PartialUpdateTransaction(String transactionId,
                        Optional<Transaction> partialTransaction);
}
