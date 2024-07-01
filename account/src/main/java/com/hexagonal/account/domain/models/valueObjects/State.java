package com.hexagonal.account.domain.models.valueObjects;

public class State {
    private final boolean state;

    public State(boolean state) {
        this.state = state;
    }

    public static State Create(boolean state) {
        return new State(state);
    }

    public boolean getValue() {
        return state;
    }
}
