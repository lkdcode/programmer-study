package run.moku.modules.gomoku.domain.model

import run.moku.modules.gomoku.domain.entity.board.BoardId
import run.moku.modules.gomoku.domain.value.MokuTurn
import run.moku.modules.gomoku.domain.value.board.MokuBoard
import run.moku.modules.gomoku.domain.value.play.MokuPlayHistory
import run.moku.modules.gomoku.domain.value.play.MokuPlayStone

class MokuPlayingModel private constructor(
    private var mokuTurn: MokuTurn,

    val boardId: BoardId = BoardId.init(),
    val mokuBoard: MokuBoard = MokuBoard.init(),
    val mokuHistory: MokuPlayHistory = MokuPlayHistory.init(),
) {

    fun playStone(playStone: MokuPlayStone): MokuPlayStatusModel {
        mokuHistory.record(playStone)
        mokuTurn.change(playStone)
        val result = mokuBoard.makeAJudgment(playStone)

        return MokuPlayStatusModel(result, playStone.mokuPlayer)
    }

    companion object {
        fun start(mokuTurn: MokuTurn): MokuPlayingModel =
            MokuPlayingModel(mokuTurn = mokuTurn)
    }
}