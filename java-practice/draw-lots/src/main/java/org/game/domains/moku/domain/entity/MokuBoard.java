package org.game.domains.moku.domain.entity;

import org.game.domains.moku.domain.value.MokuBegin;
import org.game.domains.moku.domain.value.MokuResult;

import java.util.Arrays;

public class MokuBoard {
    private final MokuPlayerCount mokuPlayerCount;
    private final int[][] value;
    private final int maxSize;

    public MokuBoard(MokuPlayerCount mokuPlayerCount, int[][] value) {
        this.mokuPlayerCount = mokuPlayerCount;
        this.value = value;
        this.maxSize = value.length;
    }

    public MokuResult beginMoku(final MokuPlayer mokuPlayer, final MokuBegin mokuBegin) {
        validBegin(mokuBegin);

        final var x = mokuBegin.x();
        final var y = mokuBegin.y();

        value[x][y] = mokuPlayer.getId();

        return MokuResult.GOING;
    }

    private void validBegin(final MokuBegin mokuBegin) {
        final var x = mokuBegin.x();
        final var y = mokuBegin.y();

        if (value.length < x || value.length < y) {
            throw new IllegalArgumentException("엉뚱한 곳에 착수 금지!");
        }

        if (value[x][y] != 0) {
            throw new IllegalArgumentException("이미 착수한 곳임!");
        }
    }

    public int[][] getBoard() {
        return Arrays.copyOf(this.value, this.value.length);
    }
}