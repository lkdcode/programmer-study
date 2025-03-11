package org.game.domains.moku.domain.entity;

import lombok.Getter;

@Getter
public class MokuPlayer {
    private final int id;

    public MokuPlayer(int id) {
        this.id = id;
    }
}
