package org.game.domains.moku.domain.entity;

import lombok.Getter;

@Getter
public class MokuWinCount {
    private final int value;

    public MokuWinCount(int value) {
        this.value = value;
    }
}
