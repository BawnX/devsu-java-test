package com.hexagonal.account.domain.models;

public class ErrorOr<T, E extends Exception> {
    private final T value;
    private final E error;

    private ErrorOr(T value, E error) {
        this.value = value;
        this.error = error;
    }

    public static <T, E extends Exception> ErrorOr<T, E> success(T value) {
        return new ErrorOr<>(value, null);
    }

    public static <T, E extends Exception> ErrorOr<T, E> failure(E error) {
        return new ErrorOr<>(null, error);
    }

    public boolean isSuccess() {
        return error == null;
    }

    public boolean isFailure() {
        return error != null;
    }

    public T getValue() {
        if (isFailure()) {
            throw new IllegalStateException("Cannot get value from a failed result");
        }
        return value;
    }

    public E getError() {
        if (isSuccess()) {
            throw new IllegalStateException("Cannot get error from a successful result");
        }
        return error;
    }
}
