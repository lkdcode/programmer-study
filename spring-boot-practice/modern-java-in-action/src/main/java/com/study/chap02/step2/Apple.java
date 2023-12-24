package com.study.chap02.step2;

class Apple {

    private final Weight weight;
    private final Color color;

    public Apple(Weight weight, Color color) {
        this.weight = weight;
        this.color = color;
    }

    public Weight getWeight() {
        return weight;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "weight=" + weight +
                ", color=" + color +
                "}";
    }

}
