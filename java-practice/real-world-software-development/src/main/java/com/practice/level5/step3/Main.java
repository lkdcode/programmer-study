package com.practice.level5.step3;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

class Main {
    public static void main(String[] args) {
        People people1 = new People("김기자", 87, "제주도");
        People people2 = new People("홍길동", 25, "서울시");
        People people3 = new People("이순신", 37, "강원도");
        People people4 = new People("이꺽정", 59, "강원도");
        People people5 = new People("박수박", 44, "경상도");
        People people6 = new People("사오정", 29, "경기도");
        People people7 = new People("손오공", 41, "경기도");
        People people8 = new People("저팔계", 57, "서울시");
        People people9 = new People("삼겹살", 24, "전라도");
        People people10 = new People("마수리", 39, "강화도"); // 팩트

        List<People> peopleList = Arrays.asList(people1, people2, people3, people4, people5, people6, people7, people8, people9, people10);

        Predicate<People> predicate1 = people -> people.getAddress().equals("강원도");
        Predicate<People> predicate2 = people -> people.getAge() >= 20;
        Predicate<People> predicate3 = people -> people.getName().startsWith("이");

        List<Predicate<People>> predicateList = Arrays.asList(predicate1, predicate2, predicate3);

        Consumer<People> consumer1 = people -> System.out.println("이름 : " + people.getName());
        Consumer<People> consumer2 = people -> System.out.println("나이 : " + people.getAge());
        Consumer<People> consumer3 = people -> System.out.println("주소 : " + people.getAddress() + "\n");

        List<Consumer<People>> consumerList = Arrays.asList(consumer1, consumer2, consumer3);

        peopleList.stream()
                .filter(people -> predicateList.stream().allMatch(predicate -> predicate.test(people)))
                .forEach(people -> consumerList.forEach(consumer -> consumer.accept(people)));
    }
}
