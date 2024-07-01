package com.hexagonal.client.domain.models.enums;

public enum GenderEnum {
    MALE("hombre"),
    FEMALE("mujer"),
    DEFAULT("default"),
    OTHER("otro");

    private final String value;

    GenderEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
