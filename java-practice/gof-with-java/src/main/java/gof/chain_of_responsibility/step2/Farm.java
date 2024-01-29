package gof.chain_of_responsibility.step2;

class Farm {

    private final FruitMiddleware fruitMiddleware;

    public Farm(FruitMiddleware fruitMiddleware) {
        this.fruitMiddleware = fruitMiddleware;
    }

    public void printPrice() {
        fruitMiddleware.print();
    }
}
