package gof.observer.step01;

import java.util.Random;

class RandomNumberGenerator extends NumberGenerator {
    private static final Random RANDOM = new Random();
    private int number;

    @Override
    int getNumber() {
        return this.number;
    }

    @Override
    void execute() {
        for (int i = 0; i < 20; i++) {
            this.number = RANDOM.nextInt(50);
            notifyObservers();
        }
    }
}
