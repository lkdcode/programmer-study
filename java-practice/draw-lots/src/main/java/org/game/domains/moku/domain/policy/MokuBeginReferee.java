package org.game.domains.moku.domain.policy;

import org.game.domains.moku.domain.entity.MokuBoard;
import org.game.domains.moku.domain.entity.MokuPlayer;
import org.game.domains.moku.domain.entity.MokuWinCount;
import org.game.domains.moku.domain.value.MokuBegin;

import java.util.Arrays;

public class MokuBeginReferee {
    private final int winCount;
    private final int playerId;

    private final int[][] board;
    private final int boardXSize;
    private final int boardYSize;

    private final int beginX;
    private final int beginy;

    public MokuBeginReferee(MokuWinCount mokuWinCount, MokuPlayer mokuPlayer, final MokuBoard board, final MokuBegin mokuBegin) {
        this.winCount = mokuWinCount.getValue();
        this.playerId = mokuPlayer.getId();
        this.board = board.getBoard();
        this.boardXSize = this.board.length;
        this.boardYSize = this.board[0].length;
        this.beginX = mokuBegin.x();
        this.beginy = mokuBegin.y();
    }

    //[ ][ ][ ][ ][ ][ ][ ]
    //[ ][ ][ ][ ][ ][ ][ ]
    //[ ][ ][0][0][ ][ ][ ]
    //[ ][ ][ ][0][ ][ ][ ]
    //[ ][ ][ ][ ][ ][ ][ ]

    public void check() {
//        final var start = board[beginX][beginy];
        final var check1 = board[beginX + 1][beginy];
        final var check2 = board[beginX - 1][beginy];

        final var check3 = board[beginX][beginy + 1];
        final var check4 = board[beginX][beginy - 1];

        final var check5 = board[beginX + 1][beginy + 1];
        final var check6 = board[beginX - 1][beginy - 1];

        final var check7 = board[beginX + 1][beginy - 1];
        final var check8 = board[beginX - 1][beginy + 1];
    }

    public static void main(String[] args) {
        final int[][] map = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 2, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 8, 0, 0},
            {0, 0, 0, 0, 1, 2, 0, 0, 0, 0},
            {0, 0, 0, 1, 2, 0, 0, 0, 0, 0},
            {0, 0, 0, 2, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };

        Arrays.stream(map)
            .forEach(e -> {
                Arrays.stream(e)
                    .forEach(o1 -> {
                        System.out.print(o1 + " ");
                    });
                System.out.println();
            });

        System.out.println(map[3][7]);
    }

    private boolean isValid(final int x, final int y) {
        return board.length > x
            && board[0].length > y
            && x >= 0
            && y >= 0
            && board[x][y] == playerId;
    }
}