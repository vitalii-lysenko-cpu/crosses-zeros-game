package com.example.crosses_zeros.ui.presentation.gameboard

import com.example.crosses_zeros.functionality.SignState

sealed interface GameBoardState {
    sealed interface Initial : GameBoardState {
        object Loading : Initial
    }

    data class Data(
        val cellList: List<SignState> = emptyList(),
        val gameEnd: Boolean = false,
        val crossedOutCells: List<Int> = emptyList(),
        val response: Int,
        val url: String,
    ) : GameBoardState
}