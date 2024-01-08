package com.go_stop.deck;

import com.go_stop.hwatoo.HwaToo;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;

public class GoStopHwaTooDeck {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final HwaToo[] HWA_TOOS = HwaToo.values();
    private static final int HWA_TOOS_SIZE = HWA_TOOS.length;
    private final Stack<HwaToo> deck;

    public GoStopHwaTooDeck() {
        this.deck = initialize();
    }

    private Stack<HwaToo> initialize() {
        final Stack<HwaToo> stack = new Stack<>();
        final Set<HwaToo> set = new HashSet<>();

        while (stack.size() != HWA_TOOS_SIZE) {
            final int index = SECURE_RANDOM.nextInt(HWA_TOOS_SIZE);
            if (set.contains(HWA_TOOS[index])) continue;

            set.add(HWA_TOOS[index]);
            stack.push(HWA_TOOS[index]);
        }

        return stack;
    }

    public boolean hasNextHwaToo() {
        return !this.deck.isEmpty();
    }

    public HwaToo getNextHwaToo() {
        if (hasNextHwaToo()) return this.deck.pop();
        throw new NoSuchElementException();
    }

}
