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

import com.hexagonal.account.application.dtos.CreateCuentaRequest;
import com.hexagonal.account.application.dtos.CuentaResponse;
import com.hexagonal.account.application.dtos.UpdateCuentaRequest;
import com.hexagonal.account.application.services.AccountService;
import com.hexagonal.account.domain.models.Account;
import com.hexagonal.account.domain.models.ErrorOr;

@RestController
@RequestMapping("cuentas")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ErrorOr<List<CuentaResponse>, RuntimeException> getAll() {
        ErrorOr<List<Account>, RuntimeException> listAccount = accountService.GetAllAccounts();
        if (listAccount.isFailure()) {
            return ErrorOr.failure(listAccount.getError());
        }

        List<CuentaResponse> listCuentaResponse = listAccount.getValue()
                .stream()
                .map(account -> CuentaResponse.fromDomain(account))
                .toList();
        return ErrorOr.success(listCuentaResponse);
    }

    @GetMapping("/{clienteId}")
    public ErrorOr<List<CuentaResponse>, RuntimeException> getByClientId(@PathVariable String clienteId) {
        ErrorOr<List<Account>, RuntimeException> listAccount = accountService.GetAccountByClientId(clienteId);
        if (listAccount.isFailure()) {
            return ErrorOr.failure(listAccount.getError());
        }

        List<CuentaResponse> listCuentaResponse = listAccount.getValue()
                .stream()
                .map(account -> CuentaResponse.fromDomain(account))
                .toList();
        return ErrorOr.success(listCuentaResponse);
    }

    @PostMapping
    public ErrorOr<CuentaResponse, RuntimeException> postCreateAccount(@RequestBody CreateCuentaRequest cuenta) {
        ErrorOr<Account, RuntimeException> account = cuenta.toDomain();
        if (account.isFailure()) {
            return ErrorOr.failure(account.getError());
        }

        account = accountService.CreateAccount(account.getValue());

        if (account.isFailure()) {
            return ErrorOr.failure(account.getError());
        }

        return ErrorOr.success(CuentaResponse.fromDomain(account.getValue()));
    }

    @PutMapping("/{cuentaId}")
    public ErrorOr<Optional<CuentaResponse>, RuntimeException> putUpdateAccount(@PathVariable String cuentaId,
            @RequestBody UpdateCuentaRequest update) {
        ErrorOr<Account, RuntimeException> accountErrorOr = update.toDomain(cuentaId);
        if (accountErrorOr.isFailure()) {
            return ErrorOr.failure(accountErrorOr.getError());
        }

        ErrorOr<Optional<Account>, RuntimeException> account = accountService.UpdateAccount(cuentaId,
                accountErrorOr.getValue());
        if (account.isFailure()) {
            return ErrorOr.failure(account.getError());
        }

        return ErrorOr.success(Optional.of(CuentaResponse.fromDomain(account.getValue().get())));
    }

    @PatchMapping("/{cuentaId}")
    public ErrorOr<Optional<CuentaResponse>, RuntimeException> patchUpdateAccount(@PathVariable String cuentaId,
            @RequestBody Optional<UpdateCuentaRequest> cuenta) {
        ErrorOr<Account, RuntimeException> accountErrorOr = cuenta.get().toDomain(cuentaId);
        if (accountErrorOr.isFailure()) {
            return ErrorOr.failure(accountErrorOr.getError());
        }

        ErrorOr<Optional<Account>, RuntimeException> accountOptional = accountService.PartialUpdateAccount(
                cuentaId,
                Optional.of(accountErrorOr.getValue()));
        if (accountOptional.isFailure()) {
            return ErrorOr.failure(accountOptional.getError());
        }

        return ErrorOr.success(Optional.of(CuentaResponse.fromDomain(accountOptional.getValue().get())));
    }

    @DeleteMapping("/{cuentaId}")
    public ErrorOr<Boolean, RuntimeException> deleteAccount(@PathVariable String cuentaId) {
        return accountService.DeleteAccount(cuentaId);
    }
}
