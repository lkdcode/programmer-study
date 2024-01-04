package com.chap04.step01;

import java.util.Arrays;
import java.util.List;

class Application {

    public static void main(String[] args) {
        List<Fruit> list = Arrays.asList(
                new Apple(3_000)
                , new Apple(2_500)
                , new Apple(2_700)
                , new Apple(2_950)
                , new Apple(2_900)
                , new Apple(3_900)
                , new Apple(3_500)
                , new Apple(3_100)
                , new Apple(1_900)
                , new Apple(3_300)
                , new Banana(800)
                , new Banana(500)
                , new Banana(650)
                , new Banana(830)
                , new Banana(730)
                , new Banana(760)
        );

        for (Fruit fruit : list) {
            if (fruit.getClass().getSimpleName().equals("Apple")) {
                if (fruit.getPrice() >= 3_000) {
                    System.out.println(fruit);
                }
            }
        }

        printDivisionLine();

        list.stream()
                .filter(e -> e.getClass().getSimpleName().equals("Apple"))
                .filter(e -> e.getPrice() >= 3_000)
                .forEach(System.out::println);

        printDivisionLine();

        for (Fruit fruit : list) {
            if (fruit.getClass().getSimpleName().equals("Banana")) {
                if (fruit.getPrice() >= 800) {
                    System.out.println(fruit.getPrice() * 12);
                }
            }
        }

        printDivisionLine();

        list.stream()
                .filter(e -> e.getClass().getSimpleName().equals("Banana"))
                .filter(e -> e.getPrice() >= 800)
                .map(e -> e.getPrice() * 12)
                .forEach(System.out::println);
    }

    private static void printDivisionLine() {
        System.out.println("===============================");
    }

}
