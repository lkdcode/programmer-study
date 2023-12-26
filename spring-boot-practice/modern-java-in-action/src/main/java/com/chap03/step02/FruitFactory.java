package com.chap03.step02;

import java.util.function.Function;

class FruitFactory {

    private static final int CODE = 3942;

    public static Fruit generate(Function<Integer, Fruit> fruit) {
        return fruit.apply(CODE);
    }

}
