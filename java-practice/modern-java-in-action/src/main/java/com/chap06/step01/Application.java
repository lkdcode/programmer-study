package com.chap06.step01;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.chap06.step01.DishType.*;
import static java.util.stream.Collectors.*;

class Application {
    public static void main(String[] args) {
        final List<Dish> dishes = Arrays.asList(
            new Dish("salmon", FISH)
            , new Dish("prawns", FISH)
            , new Dish("rice", OTHER)
            , new Dish("season fruit", OTHER)
            , new Dish("pizza", OTHER)
            , new Dish("pork", MEAT)
            , new Dish("beef", MEAT)
            , new Dish("chicken", MEAT)
        );

        final Map<DishType, List<Dish>> collect = dishes.stream()
            .collect(Collectors.groupingBy(Dish::getType));

        System.out.println(collect);

        Map<Integer, List<Dish>> collect1 = dishes.stream()
            .collect(Collectors.groupingBy(dish -> {
                if (dish.getName().length() > 3) return 3;
                if (dish.getName().length() > 4) return 4;
                if (dish.getName().length() > 5) return 5;
                if (dish.getName().length() > 6) return 6;
                return 0;
            }));

        System.out.println(collect1);

        String m1 = dishes.stream()
            .map(Dish::getName)
            .reduce((s1, s2) -> s1 + s2).get();

        Dish r1 = dishes.stream()
            .collect(reducing((s1, s2) -> new Dish(s1.getName() + s2.getName(), MEAT))).get();

//        String m2 = dishes.stream()
//            .collect(reducing((s1, s2) -> s1.getName() + s2.getName())).get();

        Map<DishType, List<Dish>> collect2 = dishes.stream()
            .collect(groupingBy(Dish::getType));

        Map<DishType, Set<Dish>> collect3 = dishes
            .stream()
            .collect(groupingBy(
                Dish::getType,
                flatMapping(dish -> collect2.get(dish.getType()).stream(), toSet())
            ));

        System.out.println("---");
        System.out.println(collect2);
        System.out.println(collect3);
        System.out.println("---");

        String m3 = dishes
            .stream()
            .map(Dish::getName)
            .reduce("", (s1, s2) -> s1 + s2);

        Map<DishType, Map<String, Map<String, List<Dish>>>> collect4 = dishes
            .stream()
            .collect(groupingBy(Dish::getType,
                groupingBy(Dish::getName
                    , groupingBy(Dish::getName))));

        Map<DishType, Long> collect5 = dishes
            .stream()
            .collect(groupingBy(Dish::getType, counting()));

        Map<DishType, Optional<Dish>> collect6 = dishes
            .stream()
            .collect(groupingBy(Dish::getType, maxBy(Comparator.comparingInt(dish -> dish.getName().length()))));

        Map<DishType, Dish> collect7 = dishes
            .stream()
            .collect(groupingBy(Dish::getType,
                collectingAndThen(
                    maxBy(Comparator.comparingInt(dish -> dish.getName().length())), Optional::get)));

        Map<DishType, Dish> collect8 = dishes
            .stream()
            .collect(toMap(
                Dish::getType,
                Function.identity(),
                BinaryOperator.maxBy(Comparator.comparingInt(dish -> dish.getName().length()))));
    }
}