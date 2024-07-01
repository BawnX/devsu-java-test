package com.hexagonal.client.domain.models.valueObjects;

import java.util.UUID;

public class Guid {
    private final String id;

    public Guid(String id) {
        this.id = id;
    }

    public static Guid Create() {
        return new Guid(UUID.randomUUID().toString()) {
        };
    }

    public String getGuid() {
        return this.id;
    }

    public UUID getUUID() {
        return UUID.fromString(this.id);
    }
}
