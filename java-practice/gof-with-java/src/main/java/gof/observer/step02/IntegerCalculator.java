package gof.observer.step02;

class IntegerCalculator extends Calculator {
    private final int a;
    private final int b;

    public IntegerCalculator(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    int a() {
        return this.a;
    }

    @Override
    int b() {
        return this.b;
    }
}
