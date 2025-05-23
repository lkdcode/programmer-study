package run.moku.modules.gomoku.domain.entity.player

@JvmInline
value class WhiteStonePlayer(
    private val mokuPlayer: MokuPlayer
) {

    fun getId() = mokuPlayer.id
}