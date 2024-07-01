package com.hexagonal.account.application.dtos;

import com.hexagonal.account.domain.models.Account;
import com.hexagonal.account.domain.models.ErrorOr;

public class CreateCuentaRequest {
    private String ClienteId;
    private int BalanceInicial;
    private Boolean Estado;
    private String TipoCuenta;

    public CreateCuentaRequest(String ClienteId, int BalanceInicial, Boolean Estado, String TipoCuenta) {
        this.ClienteId = ClienteId;
        this.BalanceInicial = BalanceInicial;
        this.Estado = Estado;
        this.TipoCuenta = TipoCuenta;
    }

    public String getClienteId() {
        return ClienteId;
    }

    public void setClienteId(String clienteId) {
        this.ClienteId = clienteId;
    }

    public int getBalanceInicial() {
        return BalanceInicial;
    }

    public void setBalanceInicial(int balanceInicial) {
        this.BalanceInicial = balanceInicial;
    }

    public Boolean getEstado() {
        return Estado;
    }

    public void setEstado(Boolean estado) {
        Estado = estado;
    }

    public String getTipoCuenta() {
        return TipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        TipoCuenta = tipoCuenta;
    }

    public ErrorOr<Account, RuntimeException> toDomain() {
        return Account.create(null, getClienteId(), Double.valueOf(getBalanceInicial()), getEstado(), getTipoCuenta(),
                null);
    }
}
