package com.study.chap02.step2;

class AppleEngine {

    private final AppleIterator appleIterator;
    private final AppleRule appleRule;

    public AppleEngine(AppleIterator appleIterator, AppleRule appleRule) {
        this.appleIterator = appleIterator;
        this.appleRule = appleRule;
    }

    public void run() {
        while (appleIterator.hasNext()) {
            Apple apple = appleIterator.next();
            appleRule.execute(apple);
        }
    }
}
