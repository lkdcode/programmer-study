package com.chap03.step02;

class Application {

    public static void main(String[] args) {
        Fruit apple1 = FruitFactory.generate(Apple::new);
        Fruit apple2 = FruitFactory.generate(Apple::new);
        Fruit apple3 = FruitFactory.generate(Apple::new);
        Fruit apple4 = FruitFactory.generate(Apple::new);

        Fruit banana1 = FruitFactory.generate(Banana::new);
        Fruit banana2 = FruitFactory.generate(Banana::new);
        Fruit banana3 = FruitFactory.generate(Banana::new);
        Fruit banana4 = FruitFactory.generate(Banana::new);

        System.out.println("apple1 = " + apple1);
        System.out.println("apple2 = " + apple2);
        System.out.println("apple3 = " + apple3);
        System.out.println("apple4 = " + apple4);

        System.out.println("banana1 = " + banana1);
        System.out.println("banana2 = " + banana2);
        System.out.println("banana3 = " + banana3);
        System.out.println("banana4 = " + banana4);
    }

}
