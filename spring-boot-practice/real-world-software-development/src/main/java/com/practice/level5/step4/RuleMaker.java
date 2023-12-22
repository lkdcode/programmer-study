package com.practice.level5.step4;

class RuleMaker<T> {

    private final CustomConsumerList<T> customConsumerList;
    private final CustomPredicate<T> customPredicateList;

    public RuleMaker(CustomConsumerList<T> customConsumerList, CustomPredicate<T> customPredicateList) {
        this.customConsumerList = customConsumerList;
        this.customPredicateList = customPredicateList;
    }

}
