package run.moku.modules.gomoku.domain.value.board

import run.moku.modules.gomoku.domain.entity.player.MokuPlayer
import run.moku.modules.gomoku.domain.value.MokuPlayResult
import run.moku.modules.gomoku.domain.value.play.MokuPlayStone

class MokuBoard private constructor(
    private val board: Array<Array<MokuPlayer?>>
) {

    private var currentRow: Int = -1
    private var currentCol: Int = -1
    private var currentPlayer: MokuPlayer? = null

    fun record(playStone: MokuPlayStone) {
        this.currentRow = playStone.getColumnIndex()
        this.currentCol = playStone.getRowIndex()
        this.currentPlayer = playStone.mokuPlayer

        this.board[currentRow][currentCol] = currentPlayer

        calculate()
    }

    private fun calculate(): MokuPlayResult {
        val horizontalCount = (check(0, -1) + check(0, +1)) + 1
        val verticalCount = (check(-1, 0) + check(+1, 0)) + 1
        val leftDiagonalCount = (check(-1, -1) + check(+1, +1)) + 1
        val rightDiagonalCount = (check(+1, -1) + check(-1, +1)) + 1

        println("horizontalCount: $horizontalCount")
        println("verticalCount: $verticalCount")
        println("leftDiagonalCount: $leftDiagonalCount")
        println("rightDiagonalCount: $rightDiagonalCount")
        println("============================================")

        if (horizontalCount == WIN_COUNT ||
            verticalCount == WIN_COUNT ||
            leftDiagonalCount == WIN_COUNT ||
            rightDiagonalCount == WIN_COUNT
        ) {
            return MokuPlayResult.VICTORY
        }

        return MokuPlayResult.IN_PROGRESS
    }

    private fun check(row: Int, col: Int, count: Int = 0, depth: Int = 1): Int {
        val addRowValue = row * depth
        val addColValue = col * depth

        val targetRow = currentRow + addRowValue
        val targetCol = currentCol + addColValue

        if ((validIndex(targetRow) && validIndex(targetCol) && validStone(targetRow, targetCol))) {
            return check(row, col, count + 1, depth + 1)
        }

        return count
    }

    private fun validIndex(target: Int): Boolean =
        target in 0..<DEFAULT_INDEX

    private fun validStone(row: Int, col: Int): Boolean =
        board[row][col] == currentPlayer

    companion object {
        const val DEFAULT_INDEX = 15
        private const val WIN_COUNT = 5

        fun init(): MokuBoard =
            MokuBoard(Array(DEFAULT_INDEX) { Array(DEFAULT_INDEX) { null } })
    }
}