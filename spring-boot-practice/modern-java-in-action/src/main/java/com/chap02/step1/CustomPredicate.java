package com.chap02.step1;

@FunctionalInterface
interface CustomPredicate<T> {
    boolean test(T t);
}
