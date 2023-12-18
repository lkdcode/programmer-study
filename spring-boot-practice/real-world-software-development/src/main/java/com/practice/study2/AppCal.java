package com.practice.study2;

public class AppCal {
    public static void main(String[] args) {
        Cal cal = new Cal();
        int result = cal.run((a, b) -> a * b);
        System.out.println(result);
    }
}
