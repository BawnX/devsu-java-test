package com.hexagonal.client.domain.models.valueObjects;

public abstract class Age {
    private final int age;

    public Age(Integer age) {
        this.age = age;
    }

    public static Age Create(Integer age) {
        if (age == null) {
            age = 0;
        }

        return new Age(age) {
        };
    }

    public int getAge() {
        return age;
    }
}
