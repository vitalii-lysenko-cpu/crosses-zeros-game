package com.example.crosses_zeros.ui.presentation.gameboard

import com.example.crosses_zeros.functionality.SignState

sealed interface GameBoardState {
    sealed interface Initial : GameBoardState {
        object Loading : Initial
    }

    data class Data(
        val cellList: List<SignState> = emptyList(),
        val response: Int,
        val url: String,
    ) : GameBoardState {

        fun getGameEnd(): Boolean =
            drawScore() ||
                    firstRow() ||
                    firstColumn() ||
                    secondRow() ||
                    secondColumn() ||
                    thirdRow() ||
                    thirdColumn() ||
                    firstDiagonal() ||
                    secondDiagonal()

        private fun secondDiagonal() =
            cellList[2] == cellList[4] &&
                    cellList[4] == cellList[6] &&
                    cellList[2] != SignState.EMPTY

        private fun firstDiagonal() =
            cellList[0] == cellList[4] &&
                    cellList[4] == cellList[8] &&
                    cellList[0] != SignState.EMPTY

        private fun thirdColumn() =
            cellList[2] == cellList[5] &&
                    cellList[5] == cellList[8] &&
                    cellList[2] != SignState.EMPTY

        private fun secondColumn() =
            cellList[1] == cellList[4] &&
                    cellList[4] == cellList[7] &&
                    cellList[1] != SignState.EMPTY

        private fun firstColumn() =
            cellList[0] == cellList[3] &&
                    cellList[3] == cellList[6] &&
                    cellList[0] != SignState.EMPTY

        private fun thirdRow() =
            cellList[6] == cellList[7] &&
                    cellList[7] == cellList[8] &&
                    cellList[6] != SignState.EMPTY

        private fun secondRow() =
            cellList[3] == cellList[4] &&
                    cellList[4] == cellList[5] &&
                    cellList[3] != SignState.EMPTY

        private fun firstRow() =
            cellList[0] == cellList[1] &&
                    cellList[1] == cellList[2] &&
                    cellList[0] != SignState.EMPTY

        private fun drawScore() =
            !cellList.contains(SignState.EMPTY)

        fun getWinState(): List<Int> {
            return if (getGameEnd()) {
                when {
                    firstRow() -> listOf(0, 1, 2)
                    secondRow() -> listOf(3, 4, 5)
                    thirdRow() -> listOf(6, 7, 8)
                    firstColumn() -> listOf(0, 3, 6)
                    secondColumn() -> listOf(1, 4, 7)
                    thirdColumn() -> listOf(2, 5, 8)
                    firstDiagonal() -> listOf(0, 4, 8)
                    secondDiagonal() -> listOf(2, 4, 6)
                    drawScore() -> emptyList()
                    else -> emptyList()
                }
            } else emptyList()
        }
    }
}