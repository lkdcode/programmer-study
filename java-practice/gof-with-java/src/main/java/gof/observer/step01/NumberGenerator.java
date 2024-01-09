package gof.observer.step01;

import java.util.ArrayList;
import java.util.List;

abstract class NumberGenerator {
    private final List<Observer> observers = new ArrayList<>();

    void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    void deleteObserver(Observer observer) {
        this.observers.remove(observer);
    }

    void notifyObservers() {
        this.observers.forEach(o -> o.update(this));
    }

    abstract int getNumber();

    abstract void execute();

}
