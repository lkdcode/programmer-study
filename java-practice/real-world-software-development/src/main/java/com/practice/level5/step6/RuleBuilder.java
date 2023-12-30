package com.practice.level5.step6;

class RuleBuilder<T extends People> {

    private final CustomPredicate<T> customPredicate;

    private RuleBuilder(CustomPredicate<T> customPredicate) {
        this.customPredicate = customPredicate;
    }

    static <T extends People> RuleBuilder<T> when(CustomPredicate<T> customPredicate) {
        return new RuleBuilder<>(customPredicate);
    }

    Rule<T> then(CustomConsumer<T> customConsumer) {
        return new Rule<T>(customPredicate, customConsumer);
    }

}
