package gof.chain_of_responsibility.step2;

public class Banana extends FruitMiddleware {
    @Override
    public void print() {
        System.out.println("Banana: 800");
        nextPrint();
    }
}
