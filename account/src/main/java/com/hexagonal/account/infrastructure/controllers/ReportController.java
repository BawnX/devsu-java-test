package com.hexagonal.account.infrastructure.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexagonal.account.application.dtos.ReportesResponse;
import com.hexagonal.account.application.services.TransactionService;
import com.hexagonal.account.domain.models.ErrorOr;

@RestController
@RequestMapping("reportes")
public class ReportController {
    private final TransactionService transactionService;

    public ReportController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ErrorOr<List<ReportesResponse>, RuntimeException> getDetailsTransactionAccount(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String cliente) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return transactionService.GetRangeTransaction(
                LocalDate.from(formatter.parse(startDate)),
                LocalDate.from(formatter.parse(endDate)),
                cliente);
    }
}
