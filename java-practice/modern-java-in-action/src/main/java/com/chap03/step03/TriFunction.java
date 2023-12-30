package com.chap03.step03;

@FunctionalInterface
interface TriFunction<T, U, V, R> {

    R apply(T t, U u, V v);

}
