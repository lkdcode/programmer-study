package com.chap04.step01;

class Apple implements Fruit {

    private final int price;

    public Apple(int price) {
        this.price = price;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "price=" + price +
                '}';
    }

}
