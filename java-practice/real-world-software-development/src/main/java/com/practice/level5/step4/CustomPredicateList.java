package com.practice.level5.step4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CustomPredicateList<T> {

    private final List<CustomPredicate<T>> customPredicateList = new ArrayList<>();
    ;

    @SafeVarargs
    public CustomPredicateList(CustomPredicate<T>... customPredicateList) {
        this.customPredicateList.addAll(Arrays.asList(customPredicateList));
    }

    public void addCustomPredicate(CustomPredicate<T> customPredicate) {
        this.customPredicateList.add(customPredicate);
    }

    public List<CustomPredicate<T>> getCustomPredicateList() {
        return new ArrayList<>(customPredicateList);
    }

}
