package com.practice.level5.step5;

@FunctionalInterface
interface CustomPredicate<T> {

    boolean valid(T t);

}
