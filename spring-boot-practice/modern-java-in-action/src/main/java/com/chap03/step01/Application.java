package com.chap03.step01;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class Application {

    public static void main(String[] args) {
        Function<String, Integer> function = Integer::parseInt;
        System.out.println(parse(function, "123"));
        System.out.println(parse(function, "528"));

        System.out.println();

        List<String> list = List.of("kiwi", "waterMelon", "banana", "apple", "pineapple");
        BiPredicate<List<String>, String> biPredicate = List::contains;

        System.out.println("apple contains ? : " + filter(biPredicate, list, "apple"));
        System.out.println("strawBerry contains ? : " + filter(biPredicate, list, "strawBerry"));
        System.out.println("melon contains ? : " + filter(biPredicate, list, "melon"));

        new MyString("apple").run();
        new MyString("banana").run();
        new MyString("pineapple").run();
        new MyString("kiwi").run();

        System.out.println();
        int number1 = 61;
        int number2 = 116;
        Calculator calculator = new Calculator(number1, number2);
        System.out.println(number1 + " * " + number2 + " = " + calculator.process((num1, num2) -> num1 * num2));
        System.out.println(number1 + " / " + number2 + " = " + calculator.process((num1, num2) -> num1 / num2));
        System.out.println(number1 + " + " + number2 + " = " + calculator.process(Integer::sum));
        System.out.println(number1 + " - " + number2 + " = " + calculator.process((num1, num2) -> num1 - num2));
        System.out.println(number1 + " % " + number2 + " = " + calculator.process((num1, num2) -> num1 % num2));
    }

    private static int parse(Function<String, Integer> function, String target) {
        return function.apply(target);
    }

    private static boolean filter(BiPredicate<List<String>, String> predicate, List<String> list, String e) {
        return predicate.test(list, e);
    }

}
