package com.hexagonal.account.application.dtos;

import com.hexagonal.account.domain.models.Account;
import com.hexagonal.account.domain.models.Transaction;

public class MovimientoResponse {
    private String NumeroCuenta;
    private String TipoCuenta;
    private int Saldo;
    private Boolean Estado;
    private int Movimiento;

    public MovimientoResponse(String NumeroCuenta, String TipoCuenta, int Saldo, Boolean Estado,
            int Movimiento) {
        this.NumeroCuenta = NumeroCuenta;
        this.TipoCuenta = TipoCuenta;
        this.Saldo = Saldo;
        this.Estado = Estado;
        this.Movimiento = Movimiento;
    }

    public String getNumeroCuenta() {
        return NumeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        NumeroCuenta = numeroCuenta;
    }

    public String getTipoCuenta() {
        return TipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        TipoCuenta = tipoCuenta;
    }

    public int getSaldo() {
        return Saldo;
    }

    public void setSaldo(int saldoInicial) {
        Saldo = saldoInicial;
    }

    public Boolean getEstado() {
        return Estado;
    }

    public void setEstado(Boolean estado) {
        Estado = estado;
    }

    public int getMovimiento() {
        return Movimiento;
    }

    public void setMovimiento(int movimiento) {
        Movimiento = movimiento;
    }

    public static MovimientoResponse fromModel(Transaction transaction, Account account) {
        return new MovimientoResponse(
                transaction.getAccountId().getGuid(),
                account.getTypeAccount().getEnum().getValue(),
                (int) account.getBalance().getValue(),
                account.getState().getValue(),
                (int) transaction.getAmount().getValue());
    }
}
