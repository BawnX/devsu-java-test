package com.hexagonal.account.application.dtos;

import com.hexagonal.account.domain.models.Account;
import com.hexagonal.account.domain.models.ErrorOr;

public class UpdateCuentaRequest {
    private int BalanceInicial;
    private String TipoCuenta;
    private Boolean Estado;

    public UpdateCuentaRequest(int BalanceInicial, String TipoCuenta, Boolean Estado) {
        this.BalanceInicial = BalanceInicial;
        this.TipoCuenta = TipoCuenta;
        this.Estado = Estado;
    }

    public int getBalanceInicial() {
        return BalanceInicial;
    }

    public void setBalanceInicial(int balanceInicial) {
        BalanceInicial = balanceInicial;
    }

    public String getTipoCuenta() {
        return TipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        TipoCuenta = tipoCuenta;
    }

    public Boolean getEstado() {
        return Estado;
    }

    public void setEstado(Boolean estado) {
        Estado = estado;
    }

    public ErrorOr<Account, RuntimeException> toDomain(String accountId) {
        return Account.create(accountId, null, Double.valueOf(getBalanceInicial()), getEstado(), getTipoCuenta(),
                null);
    }
}
