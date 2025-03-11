package org.game.domains.moku.domain.entity;

import lombok.Getter;

@Getter
public class MokuPlayerCount {
    private final int value;

    public MokuPlayerCount(int value) {
        this.value = value;
    }
}