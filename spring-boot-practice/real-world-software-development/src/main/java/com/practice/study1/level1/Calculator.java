package com.practice.study1.level1;

class Calculator {
    private final int number1;
    private final int number2;

    Calculator(int number1, int number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    int add() {
        return number1 + number2;
    }

    int subtract() {
        return number1 - number2;
    }

    int multiply() {
        return number1 * number2;
    }

    int divide() {
        return number1 / number2;
    }

    int modulus() {
        return number1 % number2;
    }
}
