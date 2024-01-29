package gof.chain_of_responsibility.step2;

class Application {
    public static void main(String[] args) {
        final FruitMiddleware fruitMiddleware = FruitMiddleware.link(
                new Apple(),
                new Banana(),
                new Kiwi(),
                new Pineapple()
        );
        final Farm farm = new Farm(fruitMiddleware);

        farm.printPrice();
    }
}
