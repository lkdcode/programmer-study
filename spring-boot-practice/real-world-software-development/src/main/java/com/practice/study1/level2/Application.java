package com.practice.study1.level2;

class Application {
    public static void main(String[] args) {
        Calculator calculator = new Calculator(23, 5);
        int result1 = calculator.calculate(new AdditionOperator());
        System.out.println("result = " + result1);

        int result2 = calculator.calculate(new SubtractOperator());
        System.out.println("result2 = " + result2);

        
    }
}
