package com.chap05.step01;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Application {
    public static void main(String[] args) {
        final Trader raoul = new Trader("Raoul", "Camgridge");
        final Trader mario = new Trader("Mario", "Milan");
        final Trader alan = new Trader("Alan", "Camgridge");
        final Trader brian = new Trader("Brian", "Camgridge");

        final List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300)
                , new Transaction(raoul, 2012, 1_000)
                , new Transaction(raoul, 2011, 400)
                , new Transaction(mario, 2012, 710)
                , new Transaction(mario, 2012, 700)
                , new Transaction(alan, 2012, 950)
        );

        // 1. 2011 년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.
        System.out.println("1. 2011 년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.");
        transactions.stream()
                .filter(e -> e.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .forEach(System.out::println);

        // 2. 거래자가 근무하는 모든 도시를 중복 없이 나열하시오.
        System.out.println("2. 거래자가 근무하는 모든 도시를 중복 없이 나열하시오.");
        transactions.stream()
                .map(e -> e.getTrader().getCity())
                .distinct()
                .forEach(System.out::println);

        // 3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.
        System.out.println("3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.");
        transactions.stream()
                .filter(e -> e.getTrader().getCity().equals("Camgridge"))
                .sorted(Comparator.comparing(e -> e.getTrader().getName()))
                .map(e -> e.getTrader().getName())
                .distinct()
                .forEach(System.out::println);

        // 4. 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오.
        System.out.println("4. 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오.");
        transactions.stream()
                .map(e -> e.getTrader().getName())
                .distinct()
                .sorted()
                .forEach(System.out::println);

        String collect = transactions.stream()
                .map(e -> e.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.joining());
        System.out.println("-- joining");
        System.out.println(collect);

        // 5. 밀라노에 거래자가 있는가?
        System.out.println("5. 밀라노에 거래자가 있는가?");
        boolean milan = transactions.stream()
                .anyMatch(e -> e.getTrader().getCity().equals("Milan"));
        System.out.println(milan);

        // 6. 케임브리지에 거주하는 거래자의 모든 트랜잭션값을 출력하시오.
        System.out.println("6. 케임브리지에 거주하는 거래자의 모든 트랜잭션값을 출력하시오.");
        transactions.stream()
                .filter(e -> e.getTrader().getCity().equals("Camgridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);

        // 7. 전체 트랜잭션 중 최댓값은 얼마인가?
        System.out.println("7. 전체 트랜잭션 중 최댓값은 얼마인가?");
        Transaction max1 = transactions.stream()
                .reduce((a, b) -> a.getValue() > b.getValue() ? a : b)
                .get();

        Transaction max2 = transactions.stream()
                .max(Comparator.comparing(Transaction::getValue))
                .get();

        System.out.println(max1);
        System.out.println(max2);

        // 8. 전체 트랜잭션 중 최솟값은 얼마인가?
        System.out.println("8. 전체 트랜잭션 중 최솟값은 얼마인가?");
        Transaction min1 = transactions.stream()
                .reduce((a, b) -> a.getValue() > b.getValue() ? b : a)
                .get();

        Transaction min2 = transactions.stream()
                .min(Comparator.comparing(Transaction::getValue))
                .get();

        System.out.println(min1);
        System.out.println(min2);
    }
}
