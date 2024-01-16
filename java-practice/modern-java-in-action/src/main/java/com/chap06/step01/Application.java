package com.chap06.step01;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;

import static com.chap06.step01.DishType.*;

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
    }
}
