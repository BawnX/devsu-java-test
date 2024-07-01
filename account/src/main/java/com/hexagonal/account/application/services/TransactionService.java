package com.hexagonal.account.application.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.hexagonal.account.application.dtos.CreateMovimientoRequest;
import com.hexagonal.account.application.dtos.MovimientoResponse;
import com.hexagonal.account.application.dtos.ReportesResponse;
import com.hexagonal.account.domain.models.ErrorOr;
import com.hexagonal.account.domain.models.Transaction;
import com.hexagonal.account.domain.ports.in.transaction.CreateTransactionUseCase;
import com.hexagonal.account.domain.ports.in.transaction.DeleteTransactionUseCase;
import com.hexagonal.account.domain.ports.in.transaction.RetrieveTransactionUseCase;
import com.hexagonal.account.domain.ports.in.transaction.UpdateTransactionUseCase;

public class TransactionService implements RetrieveTransactionUseCase, CreateTransactionUseCase,
        UpdateTransactionUseCase, DeleteTransactionUseCase {
    private final RetrieveTransactionUseCase retrieveTransactionUseCase;
    private final CreateTransactionUseCase createTransactionUseCase;
    private final UpdateTransactionUseCase updateTransactionUseCase;
    private final DeleteTransactionUseCase deleteTransactionUseCase;

    public TransactionService(CreateTransactionUseCase createTransactionUseCase,
            DeleteTransactionUseCase deleteTransactionUseCase,
            RetrieveTransactionUseCase retrieveTransactionUseCase,
            UpdateTransactionUseCase updateTransactionUseCase) {
        this.createTransactionUseCase = createTransactionUseCase;
        this.deleteTransactionUseCase = deleteTransactionUseCase;
        this.retrieveTransactionUseCase = retrieveTransactionUseCase;
        this.updateTransactionUseCase = updateTransactionUseCase;
    }

    @Override
    public ErrorOr<Optional<MovimientoResponse>, RuntimeException> GetTransaction(String transactionId) {
        return retrieveTransactionUseCase.GetTransaction(transactionId);
    }

    @Override
    public ErrorOr<List<MovimientoResponse>, RuntimeException> GetAllTransaction() {
        return retrieveTransactionUseCase.GetAllTransaction();
    }

    @Override
    public ErrorOr<List<ReportesResponse>, RuntimeException> GetRangeTransaction(LocalDate startDate,
            LocalDate endDate,
            String clientId) {
        return retrieveTransactionUseCase.GetRangeTransaction(startDate, endDate, clientId);
    }

    @Override
    public ErrorOr<MovimientoResponse, RuntimeException> CreateTransaction(CreateMovimientoRequest transaction) {
        return createTransactionUseCase.CreateTransaction(transaction);
    }

    @Override
    public ErrorOr<Optional<Transaction>, RuntimeException> UpdateTransaction(String transactionId,
            Transaction updateTransaction) {
        return updateTransactionUseCase.UpdateTransaction(transactionId, updateTransaction);
    }

    @Override
    public ErrorOr<Optional<Transaction>, RuntimeException> PartialUpdateTransaction(String transactionId,
            Optional<Transaction> partialTransaction) {
        return updateTransactionUseCase.PartialUpdateTransaction(transactionId, partialTransaction);
    }

    @Override
    public ErrorOr<Boolean, RuntimeException> DeleteTransaction(String transactionId) {
        return deleteTransactionUseCase.DeleteTransaction(transactionId);
    }

}
