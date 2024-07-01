package com.hexagonal.account.domain.ports.in.transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.hexagonal.account.application.dtos.MovimientoResponse;
import com.hexagonal.account.application.dtos.ReportesResponse;
import com.hexagonal.account.domain.models.ErrorOr;

public interface RetrieveTransactionUseCase {
    ErrorOr<Optional<MovimientoResponse>, RuntimeException> GetTransaction(String transactionId);

    ErrorOr<List<MovimientoResponse>, RuntimeException> GetAllTransaction();

    ErrorOr<List<ReportesResponse>, RuntimeException> GetRangeTransaction(LocalDate startDate, LocalDate endDate,
            String clientId);
}
