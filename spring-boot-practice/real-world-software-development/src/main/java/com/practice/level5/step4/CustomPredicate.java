package com.practice.level5.step4;

@FunctionalInterface
interface CustomPredicate<T> {

    boolean valid(T t);

}
