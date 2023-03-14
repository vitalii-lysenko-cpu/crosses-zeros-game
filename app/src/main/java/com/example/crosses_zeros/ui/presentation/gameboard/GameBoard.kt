package com.example.crosses_zeros.ui.presentation.gameboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.crosses_zeros.R
import com.example.crosses_zeros.functionality.SignState
import com.example.crosses_zeros.ui.components.Sign

@Composable
fun GameBoard(
    gameBoardViewModel: GameBoardViewModel = hiltViewModel(),
) {
    val state: Data by gameBoardViewModel.gameState.collectAsState()
    GameBoard(
        state = state,
        onClick = gameBoardViewModel::onChangeSign,
        onStartNewGame = gameBoardViewModel::onStartNewGame,
    )
}

@Composable
fun GameBoard(
    state: Data,
    onClick: (Int) -> Unit,
    onStartNewGame: () -> Unit,
) {
    Column() {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth(),
            columns = GridCells.Fixed(3)
        ) {
            items(state.cellList.size) {

                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .aspectRatio(1f)
                        .background(
                            color = colorResource(
                                id =
                                if (state.crossedOutCells.isNotEmpty()) {
                                    if (state.crossedOutCells[0] == it ||
                                        state.crossedOutCells[1] == it ||
                                        state.crossedOutCells[2] == it
                                    ) {
                                        R.color.win_cell_color
                                    } else {
                                        R.color.cell_color
                                    }

                                } else {
                                    R.color.cell_color
                                }
                            )
                        )
                        .clickable { onClick(it) }
                ) {
                    val painter = when (state.cellList[it]) {
                        SignState.X -> painterResource(id = R.drawable.ic_sing_cross)
                        SignState.O, SignState.EMPTY -> painterResource(id = R.drawable.ic_sing_zero)

                    }
                    val alpha = when (state.cellList[it]) {
                        SignState.EMPTY -> 0.0F
                        SignState.X, SignState.O -> 1.0F
                    }
                    Sign(
                        painter = painter,
                        alpha = alpha
                    )
                }
            }
        }
        if (state.gameEnd) {
            Button(onClick = onStartNewGame,
                content = { Text(text = "New Game") })
        }
    }
}


@Preview
@Composable
fun GameBoardPreview() {
    GameBoard(
//        state = GameState.Data(
//            gameEnd = false,
//            cellContent = emptyList()
//        ),
//        onClick = {}
    )
}