package com.hexagonal.chap01.domain.service;

import com.hexagonal.chap01.domain.entity.Event;
import com.hexagonal.chap01.domain.value.ParsePolicyType;

import java.util.List;
import java.util.stream.Collectors;

public class EventSearch {

    public List<Event> retrieveEvents(List<String> unParseEvents, ParsePolicyType policyType) {
        return unParseEvents.stream()
                .map(e -> Event.parsedEvent(e, policyType))
                .collect(Collectors.toList());
    }
}
