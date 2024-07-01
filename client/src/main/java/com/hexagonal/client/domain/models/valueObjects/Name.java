package com.hexagonal.client.domain.models.valueObjects;

public abstract class Name {
    private final String name;

    public Name(String name) {
        this.name = name;
    }

    public static Name Create(String name) {
        if (name == null) {
            name = "";
        }

        return new Name(name) {
        };
    }

    public String getName() {
        return name;
    }
}
