package com.practice.study1.level2;

class Calculator {
    private final int number1;
    private final int number2;

    Calculator(int number1, int number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    int calculate(Operator operator) {
        return operator.performOperation(number1, number2);
    }
}
