package com.chap06.step01;

class Dish {
    private final String name;
    private final DishType type;

    public Dish(String name, DishType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public DishType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
