package com.chap04.step01;

class Banana implements Fruit {

    private final int price;

    public Banana(int price) {
        this.price = price;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return "Banana{" +
                "price=" + price +
                '}';
    }

}
