package com.hexagonal.account.application.dtos;

import com.hexagonal.account.domain.models.Transaction;

public class CreateMovimientoRequest {
    private String CuentaId;
    private String TipoMovimiento;
    private int Monto;

    public CreateMovimientoRequest(String CuentaId, String TipoMovimiento, int Monto) {
        this.CuentaId = CuentaId;
        this.TipoMovimiento = TipoMovimiento;
        this.Monto = Monto;
    }

    public String getCuentaId() {
        return CuentaId;
    }

    public void setCuentaId(String cuentaId) {
        CuentaId = cuentaId;
    }

    public String getTipoMovimiento() {
        return TipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        TipoMovimiento = tipoMovimiento;
    }

    public int getMonto() {
        return Monto;
    }

    public void setMonto(int monto) {
        Monto = monto;
    }

    public Transaction toModel() {
        return Transaction.create(null, CuentaId, Monto, 0, null, TipoMovimiento);
    }
}
