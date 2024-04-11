package com.hexagonal.chap01.domain.entity;

import com.hexagonal.chap01.domain.policy.RegexEventParser;
import com.hexagonal.chap01.domain.policy.SplitEventParser;
import com.hexagonal.chap01.domain.value.Activity;
import com.hexagonal.chap01.domain.value.EventId;
import com.hexagonal.chap01.domain.value.ParsePolicyType;
import com.hexagonal.chap01.domain.value.Protocol;

import java.time.OffsetDateTime;

public class Event implements Comparable<Event> {
    private OffsetDateTime timestamp;
    private EventId id;
    private Protocol protocol;
    private Activity activity;

    public Event(OffsetDateTime timestamp, EventId id, Protocol protocol, Activity activity) {
        this.timestamp = timestamp;
        this.id = id;
        this.protocol = protocol;
        this.activity = activity;
    }

    public static Event parsedEvent(String unParsedEvent, ParsePolicyType policy) {
        switch (policy) {
            case REGEX -> {
                return new RegexEventParser().parseEvent(unParsedEvent);
            }
            case SPLIT -> {
                return new SplitEventParser().parseEvent(unParsedEvent);
            }
            default -> {
                throw new IllegalArgumentException("Invalid policy");
            }
        }
    }

    @Override
    public int compareTo(Event o) {
        return timestamp.compareTo(o.timestamp);
    }
}
