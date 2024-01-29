package gof.chain_of_responsibility.step2;

public class Kiwi extends FruitMiddleware {
    @Override
    public void print() {
        System.out.println("Kiwi: 500");
        nextPrint();
    }
}
