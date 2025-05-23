package run.moku.modules.gomoku.domain.value

import run.moku.modules.gomoku.domain.entity.player.BlackStonePlayer
import run.moku.modules.gomoku.domain.entity.player.WhiteStonePlayer
import run.moku.modules.gomoku.domain.value.play.MokuPlayStone

class MokuTurn private constructor(
    private val blackStonePlayer: BlackStonePlayer,
    private val whiteStonePlayer: WhiteStonePlayer,

    private var currentPlayer: MokuStone = MokuStone.BLACK_STONE,
) {

    fun change(playStone: MokuPlayStone) {
        when (this.currentPlayer) {
            MokuStone.WHITE_STONE -> {
                if (playStone.mokuPlayer.id != whiteStonePlayer.getId()) {
                    throw IllegalArgumentException("차례가 아닙니다.")
                }
            }

            MokuStone.BLACK_STONE -> {
                if (playStone.mokuPlayer.id != blackStonePlayer.getId()) {
                    throw IllegalArgumentException("차례가 아닙니다.")
                }
            }
        }

        this.currentPlayer = currentPlayer.toggle()
    }

    companion object {
        fun of(blackStonePlayer: BlackStonePlayer, whiteStonePlayer: WhiteStonePlayer): MokuTurn =
            MokuTurn(blackStonePlayer, whiteStonePlayer)
    }
}