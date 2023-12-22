package com.practice.level5.step6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

class Engine<T> {

    private final List<Rule<T>> ruleList;

    Engine() {
        this.ruleList = new ArrayList<>();
    }

    @SafeVarargs
    final void addRule(Rule<T>... rule) {
        Objects.requireNonNull(rule);
        Collections.addAll(this.ruleList, rule);
    }

    void run(T t) {
        ruleList.forEach(rule -> rule.execute(t));
    }

}
