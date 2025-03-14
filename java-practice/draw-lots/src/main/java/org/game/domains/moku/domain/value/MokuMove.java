package org.game.domains.moku.domain.value;

import lombok.Getter;
import org.game.domains.moku.domain.entity.MokuPlayer;

@Getter
public class MokuMove {
    private final MokuPlayer mokuPlayer;
    private final MokuBegin mokuBegin;

    public MokuMove(MokuPlayer mokuPlayer, MokuBegin mokuBegin) {
        this.mokuPlayer = mokuPlayer;
        this.mokuBegin = mokuBegin;
    }
}
