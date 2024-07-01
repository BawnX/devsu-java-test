package com.hexagonal.account.domain.models.valueObjects;

import java.util.UUID;

import com.hexagonal.account.domain.models.ErrorOr;

public class Guid {
    private final String id;

    public Guid(String id) {
        this.id = id;
    }

    public static Guid Create() {
        return new Guid(UUID.randomUUID().toString());
    }

    public String getGuid() {
        return this.id;
    }

    public UUID getUUID() {
        return UUID.fromString(this.id);
    }

    public static ErrorOr<Boolean, RuntimeException> isValid(String id) {
        try {
            if (id == null) {
                return ErrorOr.failure(new RuntimeException("El id no puede ser nulo"));
            }

            UUID.fromString(id);
            return ErrorOr.success(true);
        } catch (IllegalArgumentException e) {
            return ErrorOr.failure(new RuntimeException("El id no es un UUID válido"));
        }
    }
}
