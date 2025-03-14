package org.game.domains.moku.domain.dsl;

public final class MokuConfigDSL {

    public static MokuConfigDSL init() {
        return new MokuConfigDSL();
    }

    public MokuConfigDSL playerCount() {
        return this;
    }

    public MokuConfigDSL boardSize() {
        return this;
    }

    public void winCount() {
    }
}
