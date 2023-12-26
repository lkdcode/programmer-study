package com.chap03.step01;

import java.util.function.BiFunction;
import java.util.function.Supplier;

class Calculator {

    private final int number1;
    private final int number2;

    public Calculator(int number1, int number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    public int process(BiFunction<Integer, Integer, Integer> biFunction) {
        return biFunction.apply(number1, number2);
    }

}
