package com.study.chap02.step1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

class AppleList {

    private final List<Apple> appleList = new ArrayList<>();

    public AppleList(Apple... apples) {
        Objects.requireNonNull(apples);
        this.appleList.addAll(Arrays.asList(apples));
    }

    public void addApple(Apple... apples) {
        Objects.requireNonNull(apples);
        this.appleList.addAll(Arrays.asList(apples));
    }

    public List<Apple> getAppleList() {
        return appleList;
    }

    @Override
    public String toString() {
        return "AppleList{" +
                "appleList=" + appleList +
                '}';
    }
}
