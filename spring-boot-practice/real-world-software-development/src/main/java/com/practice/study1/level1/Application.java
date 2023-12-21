package com.practice.study1.level1;

class Application {
    public static void main(String[] args) {
        Calculator calculator = new Calculator(23, 14);

        int add = calculator.add();
        int subtract = calculator.subtract();
        int divide = calculator.divide();
        int multiply = calculator.multiply();
        int modulus = calculator.modulus();

        System.out.println("add = " + add);
        System.out.println("subtract = " + subtract);
        System.out.println("divide = " + divide);
        System.out.println("multiply = " + multiply);
        System.out.println("modulus = " + modulus);
    }
}
