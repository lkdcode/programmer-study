package org.game.domains.moku.domain.policy;

import org.game.domains.moku.domain.entity.MokuBoard;
import org.game.domains.moku.domain.entity.MokuPlayer;
import org.game.domains.moku.domain.entity.MokuWinCount;
import org.game.domains.moku.domain.value.MokuBegin;
import org.game.domains.moku.domain.value.MokuResult;

public class MokuBeginResultPolicy {
    private final int winCount;
    private final int playerId;

    private final int[][] board;
    private final int boardXSize;
    private final int boardYSize;

    private final int beginX;
    private final int beginY;

    public MokuBeginResultPolicy(MokuWinCount mokuWinCount, MokuPlayer mokuPlayer, final MokuBoard board, final MokuBegin mokuBegin) {
        this.winCount = mokuWinCount.getValue();
        this.playerId = mokuPlayer.getId();
        this.board = board.getBoard();
        this.boardXSize = this.board.length;
        this.boardYSize = this.board[0].length;
        this.beginX = mokuBegin.x();
        this.beginY = mokuBegin.y();
    }

    public MokuResult result() {
        return (horizontalCount() == winCount
            || verticalCount() == winCount
            || diagonalLeftCount() == winCount
            || diagonalRightCount() == winCount)
            ? MokuResult.WIN
            : MokuResult.GOING;
    }

    private int horizontalCount() {
        return 1
            + countStone(beginX, beginY, 0, 1)
            + countStone(beginX, beginY, 0, -1);
    }

    private int verticalCount() {
        return 1
            + countStone(beginX, beginY, 1, 0)
            + countStone(beginX, beginY, -1, 0);
    }

    private int diagonalLeftCount() {
        return 1
            + countStone(beginX, beginY, 1, 1)
            + countStone(beginX, beginY, -1, -1);
    }

    private int diagonalRightCount() {
        return 1
            + countStone(beginX, beginY, 1, -1)
            + countStone(beginX, beginY, -1, 1);
    }

    private int countStone(final int x, final int y, final int dx, final int dy) {
        var count = 0;
        var nx = x + dx;
        var ny = y + dy;

        while (isValid(nx, ny)) {
            count++;
            nx += dx;
            ny += dy;
        }

        return count;
    }

    private boolean isValid(final int nx, final int ny) {
        return nx >= 0
            && ny >= 0
            && nx < boardXSize
            && ny < boardYSize
            && board[nx][ny] == playerId
            ;
    }
}
