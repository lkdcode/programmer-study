package com.chap03.step03;

class Banana {

    private final String name;
    private final double weight;
    private final int code;

    public Banana(String name, double weight, int code) {
        this.name = name;
        this.weight = weight;
        this.code = code;
    }

    @Override
    public String toString() {
        return "Banana{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", code=" + code +
                '}';
    }

}
