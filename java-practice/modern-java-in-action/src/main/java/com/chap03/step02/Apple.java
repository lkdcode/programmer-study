package com.chap03.step02;

class Apple implements Fruit {
    private final int factoryCode;

    public Apple(int factoryCode) {
        this.factoryCode = factoryCode;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "factoryCode=" + factoryCode +
                '}';
    }

}
