package org.game.domains.moku.domain.value;

import java.util.HashSet;
import java.util.Set;

public class MokuBeginHistory {
    private final Set<MokuBegin> history;

    private MokuBeginHistory() {
        this.history = new HashSet<>();
    }

    public static MokuBeginHistory init() {
        return new MokuBeginHistory();
    }

    public void append(final MokuBegin mokuBegin) {
        this.history.add(mokuBegin);
    }

    private void checkDuplicate(final MokuBegin mokuBegin) {
        if (history.contains(mokuBegin)) {
            throw new IllegalArgumentException("이미 착수한 곳임!!");
        }
    }
}