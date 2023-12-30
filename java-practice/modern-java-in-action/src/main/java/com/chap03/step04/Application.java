package com.chap03.step04;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

class Application {

    public static void main(String[] args) {
        BiFunction<String, Double, Banana> factory = Banana::new;

        List<Banana> list = List.of(
                factory.apply("red", 284.23),
                factory.apply("red", 635.23),
                factory.apply("yellow", 351.23),
                factory.apply("red", 246.2613),
                factory.apply("yellow", 568.23),
                factory.apply("red", 597.23),
                factory.apply("blue", 263.23)
        );

        Predicate<Banana> predicate = banana -> banana.getColor().equals("red");
        predicate.and(banana -> banana.getWeight() > 300.0);

        List<Banana> filteredBananaList = list.stream()
                .filter(predicate)
                .toList();

        filteredBananaList.forEach(System.out::println);

        System.out.println();
        Function<Integer, Integer> function1 = a -> a + 19;
        Function<Integer, Integer> function2 = a -> a * 3;
        Function<Integer, Integer> function3 = function1.andThen(function2);
        Function<Integer, Integer> function4 = function1.compose(function2);

        System.out.println(function3.apply(15)); // 102
        System.out.println(function4.apply(15)); // 64
    }

}
