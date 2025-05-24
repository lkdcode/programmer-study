package run.moku.modules.gomoku.domain.model

import run.moku.modules.gomoku.domain.entity.player.MokuPlayer
import run.moku.modules.gomoku.domain.value.MokuPlayResult

class MokuPlayStatusModel(
    val result: MokuPlayResult,
    val player: MokuPlayer,
)