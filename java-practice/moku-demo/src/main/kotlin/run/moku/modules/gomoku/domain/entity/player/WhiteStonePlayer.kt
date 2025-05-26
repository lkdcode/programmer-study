package run.moku.modules.gomoku.domain.entity.player

import run.moku.modules.gomoku.domain.value.MokuStone

data class WhiteStonePlayer(
    val player: MokuPlayer
) {
    val stone: MokuStone = MokuStone.WHITE_STONE
}