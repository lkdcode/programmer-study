package gof.chain_of_responsibility.step2;

abstract class FruitMiddleware {
    private FruitMiddleware next;

    public static FruitMiddleware link(FruitMiddleware first, FruitMiddleware... list) {
        FruitMiddleware head = first;

        for (FruitMiddleware nextChain : list) {
            head.next = nextChain;
            head = nextChain;
        }

        return first;
    }

    public abstract void print();

    public void nextPrint() {
        if (next == null) {
            return;
        }

        next.print();
    }
}
