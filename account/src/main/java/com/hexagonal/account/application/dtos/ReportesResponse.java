package com.hexagonal.account.application.dtos;

import java.time.format.DateTimeFormatter;

import com.hexagonal.account.domain.models.Account;
import com.hexagonal.account.domain.models.Transaction;

public class ReportesResponse {
    private String Fecha;
    private String Cliente;
    private String NumeroCuenta;
    private int SaldoInicial;
    private Boolean Estado;
    private int Movimiento;
    private int SaldoDisponible;

    public ReportesResponse(String fecha, String cliente, String numeroCuenta, int saldoInicial, Boolean estado,
            int movimiento, int saldoDisponible) {
        this.Fecha = fecha;
        this.Cliente = cliente;
        this.NumeroCuenta = numeroCuenta;
        this.SaldoInicial = saldoInicial;
        this.Estado = estado;
        this.Movimiento = movimiento;
        this.SaldoDisponible = saldoDisponible;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getNumeroCuenta() {
        return NumeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        NumeroCuenta = numeroCuenta;
    }

    public int getSaldoInicial() {
        return SaldoInicial;
    }

    public void setSaldoInicial(int saldoInicial) {
        SaldoInicial = saldoInicial;
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

    public int getSaldoDisponible() {
        return SaldoDisponible;
    }

    public void setSaldoDisponible(int saldoDisponible) {
        SaldoDisponible = saldoDisponible;
    }

    public static ReportesResponse fromModel(Account account, Transaction transaction, String cliente) {
        return new ReportesResponse(
                transaction.getTransactionDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                cliente,
                account.getClientId().getGuid(),
                (int) transaction.getBalance().getValue() + (int) transaction.getAmount().getValue(),
                account.getState().getValue(),
                (int) transaction.getAmount().getValue(),
                (int) transaction.getBalance().getValue());
    }
}
