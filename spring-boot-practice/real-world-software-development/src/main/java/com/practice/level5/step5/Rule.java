package com.practice.level5.step5;

import java.util.Arrays;
import java.util.List;

class Rule<T> {
    private final CustomPredicateList<T> customPredicateList;
    private final CustomConsumerList<T> customConsumerList;

    public Rule(CustomPredicateList<T> customPredicateList, CustomConsumerList<T> customConsumerList) {
        this.customPredicateList = customPredicateList;
        this.customConsumerList = customConsumerList;
    }

    public final void run(List<T> list) {
        list.stream()
                .filter(e -> customPredicateList.getCustomPredicateList().stream().allMatch(p -> p.valid(e)))
                .forEach(e -> customConsumerList.getConsumerList().forEach(c -> c.execute(e)));
    }

}
