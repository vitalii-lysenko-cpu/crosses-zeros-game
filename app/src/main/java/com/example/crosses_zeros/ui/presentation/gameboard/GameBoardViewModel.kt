package com.example.crosses_zeros.ui.presentation.gameboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crosses_zeros.functionality.SignState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameBoardViewModel @Inject constructor() : ViewModel() {
    private val mutableGameBoardState: MutableStateFlow<List<SignState>> = MutableStateFlow(
        listOf(
            SignState.EMPTY,
            SignState.EMPTY,
            SignState.EMPTY,
            SignState.EMPTY,
            SignState.EMPTY,
            SignState.EMPTY,
            SignState.EMPTY,
            SignState.EMPTY,
            SignState.EMPTY,
        )
    )
    private val gameEnd: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val crossedOutCells: MutableStateFlow<List<Int>> = MutableStateFlow(emptyList())
    val gameState: StateFlow<Data> =
        combine(
            gameEnd,
            crossedOutCells,
            mutableGameBoardState
        ) { gameEnd,
            crossedOutCells,
            stepsList ->
            Data(
                cellList = stepsList,
                gameEnd = gameEnd,
                crossedOutCells = crossedOutCells,
            )
        }.stateIn(
            started = SharingStarted.Lazily,
            scope = viewModelScope,
            initialValue = Data(
                cellList = mutableGameBoardState.value,
                gameEnd = gameEnd.value,
                crossedOutCells = crossedOutCells.value
            )
        )

    fun onChangeSign(elementNumber: Int) {
        if (mutableGameBoardState.value[elementNumber] == SignState.EMPTY ||
            mutableGameBoardState.value[elementNumber] == element
        ) {
            mutableGameBoardState.update {
                val signStates = it.toMutableList()
                signStates[elementNumber] = element
                signStates.toList()
            }
            prepareSign()
        }
    }

    private var element: SignState = SignState.X
    private fun prepareSign() {
        checkWin()
        element = if (element == SignState.X) SignState.O else SignState.X
    }

    private fun checkWin() {
        when {
            firstRow() || secondRow() || thirdRow() ||
                    firstColumn() || secondColumn() || thirdColumn() ||
                    firstDiagonal() || secondDiagonal() -> {
                viewModelScope.launch {
                    gameEnd.emit(true)
                }
                toDrawWinLine()
            }
        }
    }

    private fun setListData(first: Int, second: Int, third: Int) {
        crossedOutCells.update {
            val list = mutableListOf(first, second, third)
            list.toList()
        }
    }

    private fun toDrawWinLine() {
        when (gameEnd.value) {
            firstRow() -> setListData(0, 1, 2)
            secondRow() -> setListData(3, 4, 5)
            thirdRow() -> setListData(6, 7, 8)
            firstColumn() -> setListData(0, 3, 6)
            secondColumn() -> setListData(1, 4, 7)
            thirdColumn() -> setListData(2, 5, 8)
            firstDiagonal() -> setListData(0, 4, 8)
            else -> setListData(2, 4, 6)
        }
    }

    private fun secondDiagonal() = mutableGameBoardState.value[2] == element &&
            mutableGameBoardState.value[4] == element &&
            mutableGameBoardState.value[6] == element
    private fun firstDiagonal() = mutableGameBoardState.value[0] == element &&
            mutableGameBoardState.value[4] == element &&
            mutableGameBoardState.value[8] == element
    private fun thirdColumn() = mutableGameBoardState.value[2] == element &&
            mutableGameBoardState.value[5] == element &&
            mutableGameBoardState.value[8] == element

    private fun secondColumn() = mutableGameBoardState.value[1] == element &&
            mutableGameBoardState.value[4] == element &&
            mutableGameBoardState.value[7] == element

    private fun firstColumn() = mutableGameBoardState.value[0] == element &&
            mutableGameBoardState.value[3] == element &&
            mutableGameBoardState.value[6] == element

    private fun thirdRow() = mutableGameBoardState.value[6] == element &&
            mutableGameBoardState.value[7] == element &&
            mutableGameBoardState.value[8] == element

    private fun secondRow() = mutableGameBoardState.value[3] == element &&
            mutableGameBoardState.value[4] == element &&
            mutableGameBoardState.value[5] == element

    private fun firstRow() = mutableGameBoardState.value[0] == element &&
            mutableGameBoardState.value[1] == element &&
            mutableGameBoardState.value[2] == element

    fun onStartNewGame() {
        mutableGameBoardState.update {
            val list = it.toMutableList()
            list.replaceAll { SignState.EMPTY }
            list.toList()
        }
        crossedOutCells.update {
            val list=it.toMutableList()
            list.clear()
            list.toList()
        }
        element = SignState.X
    }
}


