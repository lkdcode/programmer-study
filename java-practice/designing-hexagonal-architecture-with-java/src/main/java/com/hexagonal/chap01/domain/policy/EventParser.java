package com.hexagonal.chap01.domain.policy;

import com.hexagonal.chap01.domain.entity.Event;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public interface EventParser {
    DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                    .withZone(ZoneId.of("UTC"));

    Event parseEvent(String event);
}
