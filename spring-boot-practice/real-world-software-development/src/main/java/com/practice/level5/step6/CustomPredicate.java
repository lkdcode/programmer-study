package com.practice.level5.step6;

@FunctionalInterface
interface CustomPredicate<T> {

    boolean valid(T t);

}
