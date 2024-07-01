package com.hexagonal.account.infrastructure.config;

import java.util.Collections;
import java.util.List;

import com.hexagonal.account.domain.models.ErrorOr;

public class ResponseWrapper<T> {
    private List<T> data;
    private List<String> errors;

    public ResponseWrapper(List<T> data, List<String> errors) {
        this.data = data;
        this.errors = errors;
    }

    @SuppressWarnings("unchecked")
    public static <T> ResponseWrapper<T> createTry(ErrorOr<T, RuntimeException> data) {
        if (data.isFailure()) {
            return new ResponseWrapper<>(null, List.of(data.getError().getMessage()));
        }

        if (data.getValue() instanceof List<?>) {
            return new ResponseWrapper<>((List<T>) data.getValue(), List.of());
        }

        return new ResponseWrapper<>(List.of(data.getValue()), List.of());
    }

    public static <T> ResponseWrapper<T> createCatch(Exception e) {
        return new ResponseWrapper<>(Collections.emptyList(), List.of(e.getMessage()));
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
