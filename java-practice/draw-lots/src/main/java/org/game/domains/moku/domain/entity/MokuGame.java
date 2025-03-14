package org.game.domains.moku.domain.entity;

import org.game.domains.moku.domain.value.MokuGameHistory;
import org.game.domains.moku.domain.value.MokuPlayerList;

public class MokuGame {
    private final MokuGameId mokuGameId;
    private final MokuPlayerList mokuPlayerList;
    private final MokuGameHistory mokuGameHistory;
    private final MokuWinCount mokuWinCount;
    private final MokuBoard mokuBoard;

    public MokuGame(MokuGameId mokuGameId, MokuPlayerList mokuPlayerList, MokuGameHistory mokuGameHistory, MokuWinCount mokuWinCount, MokuBoard mokuBoard) {
        this.mokuGameId = mokuGameId;
        this.mokuPlayerList = mokuPlayerList;
        this.mokuGameHistory = mokuGameHistory;
        this.mokuWinCount = mokuWinCount;
        this.mokuBoard = mokuBoard;
    }
}