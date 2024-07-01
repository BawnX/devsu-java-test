package com.hexagonal.client.domain.models.valueObjects;

public abstract class Address {
    private final String address;

    public Address(String address) {
        this.address = address;
    }

    public static Address Create(String address) {
        if (address == null) {
            address = "";
        }

        return new Address(address) {
        };
    }

    public String getAddress() {
        return this.address;
    }
}
