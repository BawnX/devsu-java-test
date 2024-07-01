package com.hexagonal.client.domain.models.valueObjects;

import com.hexagonal.client.domain.models.enums.GenderEnum;

public abstract class Gender {
    private final GenderEnum gender;

    public Gender(GenderEnum gender) {
        this.gender = gender;
    }

    public static Gender Create(String gender) {
        if (gender == null) {
            return new Gender(GenderEnum.DEFAULT) {
            };
        }

        for (GenderEnum genderEnum : GenderEnum.values()) {
            if (genderEnum.getValue().equalsIgnoreCase(gender.toLowerCase())) {
                return new Gender(genderEnum) {
                };
            }
        }

        return new Gender(GenderEnum.DEFAULT) {
        };
    }

    public String getGender() {
        return gender.getValue();
    }
}
