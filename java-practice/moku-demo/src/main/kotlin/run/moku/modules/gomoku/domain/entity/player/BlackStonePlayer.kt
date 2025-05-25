package run.moku.modules.gomoku.domain.entity.player

import run.moku.modules.gomoku.domain.value.MokuStone

data class BlackStonePlayer(
    val player: MokuPlayer
) {
    val stone: MokuStone = MokuStone.BLACK_STONE
}