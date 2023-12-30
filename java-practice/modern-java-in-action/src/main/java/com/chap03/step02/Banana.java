package com.chap03.step02;

class Banana implements Fruit {
    private final int factoryCode;

    public Banana(int factoryCode) {
        this.factoryCode = factoryCode;
    }

    @Override
    public String toString() {
        return "Banana{" +
                "factoryCode=" + factoryCode +
                '}';
    }

}
