package com.chap03.step03;

class Application {

    public static void main(String[] args) {
        TriFunction<String, Double, Integer, Banana> triFunction = Banana::new;

        Banana banana = triFunction.apply("바나나", 35.253, 1248);
        System.out.println(banana);

    }

}
