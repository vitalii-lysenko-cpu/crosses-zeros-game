package com.example.crosses_zeros.ui.presentation.gameboard

import com.example.crosses_zeros.functionality.SignState
import com.example.crosses_zeros.functionality.data.entity.LastUrl

data class GameBoardState(
    val cellList: List<SignState> = emptyList(),
    val gameEnd: Boolean = false,
    val crossedOutCells: List<Int> = emptyList(),
    val response: Int,
    val url: LastUrl,
)
