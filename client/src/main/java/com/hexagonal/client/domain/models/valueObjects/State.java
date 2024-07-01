package com.hexagonal.client.domain.models.valueObjects;

public abstract class State {
    private final boolean state;

    public State(boolean state) {
        this.state = state;
    }

    public static State Create(boolean state) {
        return new State(state) {
        };
    }

    public boolean getState() {
        return state;
    }
}
