package gof.flyweight.step2;

import gof.flyweight.step2.files.*;

import java.util.function.Supplier;

public enum BigSets {
    BIG_0('0', Big0::new),
    BIG_1('1', Big1::new),
    BIG_2('2', Big2::new),
    BIG_3('3', Big3::new),
    BIG_4('4', Big4::new),
    BIG_5('5', Big5::new),
    BIG_6('6', Big6::new),
    ;

    private final char key;
    private final Supplier<BigChar> instance;

    BigSets(char key, Supplier<BigChar> instance) {
        this.key = key;
        this.instance = instance;
    }

    public BigChar get() {
        return this.instance.get();
    }

    public char getKey() {
        return this.key;
    }
}
