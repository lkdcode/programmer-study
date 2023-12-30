package com.chap03.step04;

class Banana {

    private final String color;
    private final double weight;

    public Banana(String color, double weight) {
        this.color = color;
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Banana{" +
                "color='" + color + '\'' +
                ", weight=" + weight +
                '}';
    }

}
