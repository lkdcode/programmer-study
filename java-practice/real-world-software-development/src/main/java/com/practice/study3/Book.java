package com.practice.study3;

import java.util.function.Predicate;

public class Book {
    private final String name;

    public Book(String name) {
        this.name = name;
    }

    public void process(Predicate<String> predicate, Predicate<String> predicate2) {
        Predicate<String> or = predicate.or(predicate2);
        if (or.test(name)) {
            System.out.println("성공, 책 이름은 : " + name);
        }
    }
}
