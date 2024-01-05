package com.chap05.step02;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Application {

    public static void main(String[] args) {
        example5();
    }

    private static void example1() {
        IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a ->
                        IntStream.rangeClosed(a, 100)
                                .filter(b -> Math.sqrt((a * a) + (b * b)) % 1 == 0)
                                .mapToObj(b ->
                                        new int[]{a, b, (int) Math.sqrt((a * a) + (b * b))}))
                .limit(3)
                .forEach(arr -> System.out.println(Arrays.toString(arr)));
    }

    private static void example2() {
        IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a ->
                        IntStream.rangeClosed(a, 100)
                                .mapToObj(b -> new double[]{a, b, Math.sqrt((a * a) + (b * b))})
                                .filter(arr -> arr[2] % 1 == 0))
                .forEach(arr -> System.out.println(Arrays.toString(arr)));
    }

    private static void example3() {
        IntStream.rangeClosed(1, 9)
                .forEach(a -> {
                    IntStream.rangeClosed(1, 9)
                            .forEach(b -> System.out.print(a + " * " + b + " = " + (a * b) + "    "));
                    System.out.println();
                });
    }

    private static void example4() {
        Stream.iterate(0, n -> n + 2)
                .limit(50)
                .forEach(System.out::println);
    }

    private static void example5() {
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(20)
                .map(e -> e[0])
                .forEach(System.out::println);
    }

}
