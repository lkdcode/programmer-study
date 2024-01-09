package gof.observer.step02;

import java.util.ArrayList;
import java.util.List;

abstract class Calculator {
    private final List<Formula> list = new ArrayList<>();

    void addFormula(Formula formula) {
        this.list.add(formula);
    }

    void removeFormula(Formula formula) {
        this.list.remove(formula);
    }

    void execute() {
        this.list.forEach(f -> f.execute(a(), b()));
    }

    abstract int a();

    abstract int b();
}
