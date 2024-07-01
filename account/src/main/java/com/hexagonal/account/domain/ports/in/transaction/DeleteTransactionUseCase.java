package com.hexagonal.account.domain.ports.in.transaction;

import com.hexagonal.account.domain.models.ErrorOr;

public interface DeleteTransactionUseCase {
    ErrorOr<Boolean, RuntimeException> DeleteTransaction(String transactionId);
}
