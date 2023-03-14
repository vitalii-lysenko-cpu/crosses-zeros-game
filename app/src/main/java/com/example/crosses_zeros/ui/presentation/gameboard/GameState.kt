package com.example.crosses_zeros.ui.presentation.gameboard

import com.example.crosses_zeros.functionality.SignState

data class Data(
    val cellList: List<SignState> = emptyList(),
    val gameEnd: Boolean = false,
    val crossedOutCells: List<Int> = emptyList(),
)
