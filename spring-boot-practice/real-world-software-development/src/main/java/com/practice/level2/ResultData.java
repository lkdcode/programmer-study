package com.practice.level2;

public class ResultData {
    private final double result;

    public ResultData(double result) {
        this.result = result;
    }

    public double getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "result=" + result +
                '}';
    }
}
