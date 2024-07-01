package com.hexagonal.account.domain.ports.in.transaction;

import com.hexagonal.account.application.dtos.CreateMovimientoRequest;
import com.hexagonal.account.application.dtos.MovimientoResponse;
import com.hexagonal.account.domain.models.ErrorOr;

public interface CreateTransactionUseCase {
    ErrorOr<MovimientoResponse, RuntimeException> CreateTransaction(CreateMovimientoRequest transaction);
}
