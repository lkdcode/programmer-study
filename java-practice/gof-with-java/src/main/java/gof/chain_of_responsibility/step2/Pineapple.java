package gof.chain_of_responsibility.step2;

public class Pineapple extends FruitMiddleware {
    @Override
    public void print() {
        System.out.println("Pineapple: 1_500");
        nextPrint();
    }
}
