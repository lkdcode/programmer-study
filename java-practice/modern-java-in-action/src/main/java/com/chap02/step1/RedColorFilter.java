package com.chap02.step1;

class RedColorFilter implements CustomPredicate<Apple> {
    @Override
    public boolean test(Apple apple) {
        return apple.getColor() == Color.RED;
    }

}
