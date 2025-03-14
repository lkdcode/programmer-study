package org.game.domains.moku.domain.value;

import java.util.Stack;

public class MokuGameHistory {
    private final MokuBeginHistory mokuMoveHistory;
    private final Stack<MokuMove> history;

    public MokuGameHistory(MokuBeginHistory mokuMoveHistory, Stack<MokuMove> history) {
        this.mokuMoveHistory = mokuMoveHistory;
        this.history = history;
    }

    public static MokuGameHistory init(final MokuBeginHistory mokuMoveHistory) {
        return new MokuGameHistory(mokuMoveHistory, new Stack<>());
    }

    public void append(final MokuMove mokuMove) {
        this.history.add(mokuMove);
        this.mokuMoveHistory.append(mokuMove.getMokuBegin());
    }

    private void checkDuplicate(final MokuMove mokuMove) {
        if (history.contains(mokuMove)) {
            throw new IllegalArgumentException("이미 착수한 곳임!!");
        }
    }
}