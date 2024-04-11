package com.hexagonal.chap01.domain.value;

public class EventId {
    private final String id;

    private EventId(String id) {
        this.id = id;
    }

    public static EventId of(String id) {
        return new EventId(id);
    }
}
