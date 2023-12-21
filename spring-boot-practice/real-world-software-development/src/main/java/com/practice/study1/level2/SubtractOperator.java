package com.practice.study1.level2;

public class SubtractOperator implements Operator {
    @Override
    public int performOperation(int number1, int number2) {
        return number1 - number2;
    }
}
