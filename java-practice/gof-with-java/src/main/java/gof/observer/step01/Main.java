package gof.observer.step01;

class Main {
    public static void main(String[] args) {
        final NumberGenerator generator = new RandomNumberGenerator();
        final Observer digitObserver = new DigitObserver();
        final Observer graphObserver = new GraphObserver();

        generator.addObserver(digitObserver);
        generator.addObserver(graphObserver);
        generator.execute();
    }
}
