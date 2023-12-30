package com.practice.study3;

public class AppBook {
    public static void main(String[] args) {
        Book book = new Book("이것이 자바다");
        book.process(e -> e.startsWith("것"),
                e -> e.endsWith("다"));
    }
}
