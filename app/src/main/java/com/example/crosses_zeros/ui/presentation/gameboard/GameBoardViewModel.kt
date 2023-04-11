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
    private val emptyCellsList = listOf(
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
        MutableStateFlow(emptyCellsList)
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
            dataStore.readName,
            response,
            mutableGameBoardState
        ) { lastUrl,
            response,
            stepsList ->
            GameBoardState.Data(
                cellList = stepsList,
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
        element = if (element == SignState.X) SignState.O else SignState.X
    }

    fun onStartNewGame() {
        mutableGameBoardState.update { emptyCellsList }
        element = SignState.X
    }

    fun saveLastUrl(url: String) {
        viewModelScope.launch {
            dataStore.saveName(url)
        }
    }
}



