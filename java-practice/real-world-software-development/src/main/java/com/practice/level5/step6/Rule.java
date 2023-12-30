package com.practice.level5.step6;

class Rule<T> {

    private final CustomPredicate<T> customPredicate;
    private final CustomConsumer<T> customConsumer;

    Rule(CustomPredicate<T> customPredicate, CustomConsumer<T> customConsumer) {
        this.customPredicate = customPredicate;
        this.customConsumer = customConsumer;
    }

    void execute(final T t) {
        if (customPredicate.valid(t)) {
            customConsumer.execute(t);
        }
    }

}
