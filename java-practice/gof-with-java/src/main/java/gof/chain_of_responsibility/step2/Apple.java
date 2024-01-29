package gof.chain_of_responsibility.step2;

public class Apple extends FruitMiddleware{
    @Override
    public void print() {
        System.out.println("Apple: 1_000");
        nextPrint();
    }
}
