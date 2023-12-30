package com.chap02.step2;

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
        return new ArrayList<>(this.appleList);
    }

    @Override
    public String toString() {
        return "AppleList{" +
                "appleList=" + appleList +
                '}';
    }
}
