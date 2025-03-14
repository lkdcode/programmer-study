package org.game.domains.moku.domain.value;

import org.game.domains.moku.domain.entity.MokuPlayer;

import java.util.ArrayList;
import java.util.List;

public class MokuPlayerList {
    private final List<MokuPlayer> list;

    public MokuPlayerList() {
        this.list = new ArrayList<>();
    }

    public void append(final MokuPlayer mokuPlayer) {
        this.list.add(mokuPlayer);
    }
}