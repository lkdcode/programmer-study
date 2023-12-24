package com.study.chap02.step2;

class Weight {

    private final int weight;

    public Weight(int weight) {
        this.weight = weight;
    }

    public int getValue() {
        return weight;
    }

    @Override
    public String toString() {
        return "Weight{" +
                "weight=" + weight +
                '}';
    }
}
