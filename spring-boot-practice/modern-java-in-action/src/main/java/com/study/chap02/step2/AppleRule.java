package com.study.chap02.step2;

class AppleRule {
    private final ApplePredicate applePredicate;
    private final AppleConsumer appleConsumer;

    public AppleRule(ApplePredicate applePredicate, AppleConsumer appleConsumer) {
        this.applePredicate = applePredicate;
        this.appleConsumer = appleConsumer;
    }

    public void execute(Apple apple) {
        if (applePredicate.test(apple)) {
            appleConsumer.accept(apple);
        }
    }

}
