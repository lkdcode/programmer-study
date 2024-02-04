package gof.flyweight.step2;

import gof.flyweight.step2.files.BigChar;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BigFactory {
    private static final BigFactory singleton = new BigFactory();
    private static final Map<Character, BigChar> pool = new HashMap<>();

    private BigFactory() {
    }


    public static BigFactory getInstance() {
        return singleton;
    }

    public synchronized BigChar getBigChar(char ch) {
        Arrays.stream(BigSets.values())
                .filter(e -> e.getKey() == ch && pool.get(ch) == null)
                .findFirst()
                .ifPresent(e -> pool.put(ch, e.get()));

        return pool.get(ch);
    }
}
