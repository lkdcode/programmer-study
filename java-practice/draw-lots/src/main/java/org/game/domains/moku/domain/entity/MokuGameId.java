package org.game.domains.moku.domain.entity;

import lombok.Getter;

@Getter
public class MokuGameId {
    private final Long id;

    public MokuGameId(Long id) {
        this.id = id;
    }
}
