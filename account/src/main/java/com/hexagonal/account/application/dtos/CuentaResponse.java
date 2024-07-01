package com.hexagonal.account.application.dtos;

import com.hexagonal.account.domain.models.Account;

public class CuentaResponse {
    private String CuentaId;
    private String ClienteId;
    private Double BalanceInicial;
    private Boolean Estado;
    private String TipoCuenta;

    public CuentaResponse(String cuentaId, String clienteId, Double balanceInicial, Boolean estado, String tipoCuenta) {
        CuentaId = cuentaId;
        ClienteId = clienteId;
        BalanceInicial = balanceInicial;
        Estado = estado;
        TipoCuenta = tipoCuenta;
    }

    public static CuentaResponse fromDomain(Account account) {
        return new CuentaResponse(account.getId().getGuid(),
                account.getClientId().getGuid(),
                account.getBalance().getValue(),
                account.getState().getValue(),
                account.getType().getEnum().getValue());
    }

    public String getCuentaId() {
        return CuentaId;
    }

    public void setCuentaId(String cuentaId) {
        CuentaId = cuentaId;
    }

    public String getClienteId() {
        return ClienteId;
    }

    public void setClienteId(String clienteId) {
        ClienteId = clienteId;
    }

    public Double getBalanceInicial() {
        return BalanceInicial;
    }

    public void setBalanceInicial(Double balanceInicial) {
        BalanceInicial = balanceInicial;
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

}
