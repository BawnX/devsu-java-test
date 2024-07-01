package com.hexagonal.client.domain.models.valueObjects;

public abstract class Cellphone {
    private final String cellphone;

    public Cellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public static Cellphone Create(String cellphone) {
        if (cellphone == null) {
            cellphone = "";
        }

        if (cellphone.length() > 15) {
            throw new RuntimeException("Cellphone number should not exceed 15 characters");
        }

        if (!cellphone.matches("^([0-9]*)$")) {
            throw new RuntimeException("Invalid cellphone number");
        }

        return new Cellphone(cellphone) {
        };
    }

    public String getCellphone() {
        return cellphone;
    }
}
