package com.chap03.step01;

import java.util.function.Predicate;

class MyString {

    private final String str;

    public MyString(String str) {
        this.str = str;
    }

    public void run() {
        Predicate<String> predicate = this::startsWith;

        if (predicate.test(str)) {
            System.out.println("startsWith 'a' : " + str);
        } else {
            System.out.println("non startsWith 'a' : " + str);
        }
    }

    private boolean startsWith(String string) {
        return string.startsWith("a");
    }

}
