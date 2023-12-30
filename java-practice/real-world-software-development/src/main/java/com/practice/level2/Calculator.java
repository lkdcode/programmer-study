package com.practice.level2;

public class Calculator {
    public ResultData process(NumbersData numbersData, CalculatorFormula calculatorFormula) {
        return calculatorFormula.formula(numbersData);
    }
}
