package com.springLessons.deviceManagement.databaseEntities;

public enum State {
    AVAILABLE("available"), IN_USE("in-use"), INACTIVE("inactive");

    public final String label;

    private State(String label) {
        this.label = label;
    }
}