package com.example.crosses_zeros.ui.presentation.gameboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crosses_zeros.functionality.CheckConnectionUseCase
import com.example.crosses_zeros.functionality.GetErrorResponseUseCase
import com.example.crosses_zeros.functionality.SignState
import com.example.crosses_zeros.functionality.data.datastore.AppDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GameBoardViewModel @Inject constructor(
    private val getErrorResponseUseCase: GetErrorResponseUseCase,
    private val checkConnectionUseCase: CheckConnectionUseCase,
    private val dataStore: AppDataStore
) : ViewModel() {
    private val emptyList = listOf(
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
    private val mutableGameBoardState: MutableStateFlow<List<SignState>> =
        MutableStateFlow(emptyList)
    private val gameEnd: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val crossedOutCells: MutableStateFlow<List<Int>> = MutableStateFlow(emptyList())
    private var response: MutableStateFlow<Int> = MutableStateFlow(0)

    init {
        viewModelScope.launch {
            if (checkConnectionUseCase.invoke()) {
                response.emit(getErrorResponseUseCase.invoke())
            } else {
                response.emit(404)
            }
        }
    }

    val gameState: StateFlow<GameBoardState> =
        combine(
            gameEnd,
            crossedOutCells,
            dataStore.readName,
            response,
            mutableGameBoardState
        ) { gameEnd,
            crossedOutCells,
            lastUrl,
            response,
            stepsList ->
            GameBoardState.Data(
                cellList = stepsList,
                gameEnd = gameEnd,
                crossedOutCells = crossedOutCells,
                response = response,
                url = lastUrl,
            )
        }.stateIn(
            started = SharingStarted.Lazily,
            scope = viewModelScope,
            initialValue = GameBoardState.Initial.Loading
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

    private fun secondDiagonal() =
        mutableGameBoardState.value[2] == element &&
                mutableGameBoardState.value[4] == element &&
                mutableGameBoardState.value[6] == element

    private fun firstDiagonal() =
        mutableGameBoardState.value[0] == element &&
                mutableGameBoardState.value[4] == element &&
                mutableGameBoardState.value[8] == element

    private fun thirdColumn() =
        mutableGameBoardState.value[2] == element &&
                mutableGameBoardState.value[5] == element &&
                mutableGameBoardState.value[8] == element

    private fun secondColumn() =
        mutableGameBoardState.value[1] == element &&
                mutableGameBoardState.value[4] == element &&
                mutableGameBoardState.value[7] == element

    private fun firstColumn() =
        mutableGameBoardState.value[0] == element &&
                mutableGameBoardState.value[3] == element &&
                mutableGameBoardState.value[6] == element

    private fun thirdRow() =
        mutableGameBoardState.value[6] == element &&
                mutableGameBoardState.value[7] == element &&
                mutableGameBoardState.value[8] == element

    private fun secondRow() =
        mutableGameBoardState.value[3] == element &&
                mutableGameBoardState.value[4] == element &&
                mutableGameBoardState.value[5] == element

    private fun firstRow() =
        mutableGameBoardState.value[0] == element &&
                mutableGameBoardState.value[1] == element &&
                mutableGameBoardState.value[2] == element

    fun onStartNewGame() {
        mutableGameBoardState.update {
            val list = it.toMutableList()
            list.clear()
            list.addAll(emptyList)
            list.toList()
        }
        crossedOutCells.update {
            val list = it.toMutableList()
            list.clear()
            list.toList()
        }
        element = SignState.X
        viewModelScope.launch {
            gameEnd.emit(false)
        }
    }

    fun saveLastUrl(url: String) {
        viewModelScope.launch {
            // lastUrl.emit(url)
            dataStore.saveName(url)
        }
    }
}



