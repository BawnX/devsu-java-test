package com.hexagonal.account.application.useCases.transactions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hexagonal.account.application.dtos.MovimientoResponse;
import com.hexagonal.account.application.dtos.ReportesResponse;
import com.hexagonal.account.domain.models.Account;
import com.hexagonal.account.domain.models.ErrorOr;
import com.hexagonal.account.domain.models.Transaction;
import com.hexagonal.account.domain.models.valueObjects.Guid;
import com.hexagonal.account.domain.ports.in.transaction.RetrieveTransactionUseCase;
import com.hexagonal.account.domain.ports.out.AccountRepositoryPort;
import com.hexagonal.account.domain.ports.out.TransactionRepositoryPort;

public class RetrieveTransactionUseCaseImpl implements RetrieveTransactionUseCase {
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final AccountRepositoryPort accountRepositoryPort;

    public RetrieveTransactionUseCaseImpl(
            TransactionRepositoryPort transactionRepositoryPort,
            AccountRepositoryPort accountRepositoryPort) {
        this.transactionRepositoryPort = transactionRepositoryPort;
        this.accountRepositoryPort = accountRepositoryPort;
    }

    @Override
    public ErrorOr<Optional<MovimientoResponse>, RuntimeException> GetTransaction(String transactionId) {
        try {
            ErrorOr<Boolean, RuntimeException> validId = Guid.isValid(transactionId);
            if (validId.isFailure()) {
                return ErrorOr.failure(validId.getError());
            }

            Optional<Transaction> transaction = transactionRepositoryPort.findByTransactionId(transactionId);
            if (!transaction.isPresent()) {
                return ErrorOr.success(Optional.empty());
            }

            Optional<Account> account = accountRepositoryPort
                    .findByAccountId(transaction.get().getAccountId().getGuid());

            if (!account.isPresent()) {
                return ErrorOr.failure(new RuntimeException("No se pudo Obtener la cuenta"));
            }

            return ErrorOr.success(Optional.of(
                    MovimientoResponse.fromModel(transaction.get(), account.get())));
        } catch (Exception e) {
            return ErrorOr.failure(new RuntimeException("No se pudieron obtener las transaccion: " + e.getMessage()));
        }
    }

    @Override
    public ErrorOr<List<MovimientoResponse>, RuntimeException> GetAllTransaction() {
        try {
            List<Transaction> transactions = transactionRepositoryPort.findAll();

            List<MovimientoResponse> responses = List.of();
            for (Transaction transaction : transactions) {
                Optional<Account> account = accountRepositoryPort.findByAccountId(transaction.getAccountId().getGuid());
                if (!account.isPresent()) {
                    continue;
                }

                responses.add(MovimientoResponse.fromModel(transaction, account.get()));
            }

            return ErrorOr.success(responses);
        } catch (Exception e) {
            return ErrorOr.failure(new RuntimeException("No se pudieron obtener las transacciones: " + e.getMessage()));
        }
    }

    @Override
    public ErrorOr<List<ReportesResponse>, RuntimeException> GetRangeTransaction(LocalDate startDate,
            LocalDate endDate,
            String clientId) {
        try {
            ErrorOr<Boolean, RuntimeException> validId = Guid.isValid(clientId);
            if (validId.isFailure()) {
                return ErrorOr.failure(validId.getError());
            }

            List<Account> accounts = accountRepositoryPort.findByClientId(clientId);
            if (accounts.isEmpty()) {
                return ErrorOr.failure(new RuntimeException("No se encontró el cliente"));
            }

            List<Transaction> transactions = new ArrayList<>();

            for (Account account : accounts) {
                List<Transaction> accountTransactions = transactionRepositoryPort
                        .findByAccountId(account.getId().getGuid());
                transactions.addAll(accountTransactions);
            }

            List<Transaction> filteredTransactions = transactions.stream()
                    .filter(t -> t.getTransactionDateTime().isAfter(startDate.atStartOfDay().minusDays(1)) &&
                            t.getTransactionDateTime().isBefore(endDate.atTime(23, 59, 59).plusDays(1)))
                    .collect(Collectors.toList());

            List<ReportesResponse> responses = new ArrayList<>();
            for (Transaction transaction : filteredTransactions) {
                Optional<Account> account = accounts.stream()
                        .filter(a -> a.getId().getGuid().equals(transaction.getAccountId().getGuid()))
                        .findFirst();

                if (!account.isPresent()) {
                    continue;
                }
                ReportesResponse response = ReportesResponse.fromModel(account.get(), transaction, "");
                responses.add(response);
            }

            return ErrorOr.success(responses);
        } catch (Exception e) {

        }
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
