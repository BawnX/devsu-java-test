package com.hexagonal.account.infrastructure.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexagonal.account.application.dtos.CreateMovimientoRequest;
import com.hexagonal.account.application.dtos.MovimientoResponse;
import com.hexagonal.account.application.services.TransactionService;
import com.hexagonal.account.domain.models.ErrorOr;
import com.hexagonal.account.domain.models.Transaction;

@RestController
@RequestMapping("movimientos")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ErrorOr<List<MovimientoResponse>, RuntimeException> getAll() {
        return transactionService.GetAllTransaction();
    }

    @GetMapping("/{movimientoId}")
    public ErrorOr<Optional<MovimientoResponse>, RuntimeException> getTransaction(@PathVariable String movimientoId) {
        return transactionService.GetTransaction(movimientoId);
    }

    @PostMapping
    public ErrorOr<MovimientoResponse, RuntimeException> postCreateTransaction(
            @RequestBody CreateMovimientoRequest transaction) {
        return transactionService.CreateTransaction(transaction);
    }

    @PutMapping("/{movimientoId}")
    public ErrorOr<Optional<Transaction>, RuntimeException> putUpdateTransaction(@PathVariable String movimientoId,
            @RequestBody Transaction updateTransaction) {
        return transactionService.UpdateTransaction(movimientoId, updateTransaction);
    }

    @PatchMapping("/{movimientoId}")
    public ErrorOr<Optional<Transaction>, RuntimeException> patchPartialUpdateTransaction(
            @PathVariable String movimientoId, @RequestBody Optional<Transaction> updateTransaction) {
        return transactionService.PartialUpdateTransaction(movimientoId, updateTransaction);
    }

    @DeleteMapping("/movimientoId")
    public ErrorOr<Boolean, RuntimeException> deleteTransaction(@PathVariable String movimientoId) {
        return transactionService.DeleteTransaction(movimientoId);
    }
}
