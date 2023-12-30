package com.practice.level2;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Calculator calculator = new Calculator();


        ResultData multiple = calculator.process(new NumbersData(List.of(1, 2, 3, 4)), Main::getResultData);


        ResultData plus = calculator.process(new NumbersData(List.of(1, 2, 3, 4)),
                e -> new ResultData(e.getList().stream().mapToDouble(Integer::doubleValue).sum()));


        ResultData minus = calculator.process(new NumbersData(List.of(1, 2, 3, 4)), e -> {
            double result = 0;

            for (Integer integer : e.getList()) {
                result -= integer;
            }

            return new ResultData(result);
        });

        System.out.println("multiple = " + multiple);
        System.out.println("plus = " + plus);
        System.out.println("minus = " + minus);
    }

    private static ResultData getResultData(NumbersData e) {
        double result = 1;

        for (Integer integer : e.getList()) {
            if (integer == 0) return new ResultData(0);
            result += result * integer;
        }

        return new ResultData(result);
    }
}
