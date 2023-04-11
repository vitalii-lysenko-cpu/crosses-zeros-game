package com.example.crosses_zeros.ui.presentation.gameboard

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.crosses_zeros.R
import com.example.crosses_zeros.functionality.SignState
import com.example.crosses_zeros.ui.components.Sign

@Composable
fun GameBoard(
    gameBoardViewModel: GameBoardViewModel = hiltViewModel(),
) {
    val state: GameBoardState by gameBoardViewModel.gameState.collectAsState()
    GameBoard(
        state = state,
        onClick = gameBoardViewModel::onChangeSign,
        onStartNewGame = gameBoardViewModel::onStartNewGame,
        saveUrl = gameBoardViewModel::saveLastUrl,
    )
}


@Composable
fun GameBoard(
    state: GameBoardState,
    onClick: (Int) -> Unit,
    onStartNewGame: () -> Unit,
    saveUrl: (String) -> Unit,
) {
    when (state) {
        is GameBoardState.Initial.Loading -> CircularProgressIndicator(color = Color.Blue)
        is GameBoardState.Data -> {
            if (state.response != 404) {
                Game(state, onClick, onStartNewGame)
            } else {
                MyContent(state, saveUrl)
            }
        }
    }
}

@Composable
fun Game(
    state: GameBoardState.Data,
    onClick: (Int) -> Unit,
    onStartNewGame: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .background(color = colorResource(id = R.color.greeting_background)),
            contentAlignment = Alignment.Center,

            ) {
            Text(
                modifier = Modifier.padding(24.dp),
                text = "WELCOME IN GAME",
                fontSize = 36.sp,
                color = colorResource(id = R.color.greeting_color),
                textAlign = TextAlign.Center,
            )
        }
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.grid_background_color)),
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
                                if (state.getWinState().isNotEmpty()) {
                                    if (state.getWinState()[0] == it ||
                                        state.getWinState()[1] == it ||
                                        state.getWinState()[2] == it
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
        if (state.getGameEnd()) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                onClick = onStartNewGame,
            ) { Text(text = "New Game") }
        }
    }
}

@Composable
fun MyContent(
    state: GameBoardState.Data,
    saveUrl: (String) -> Unit
) {
    AndroidView(factory = {
        WebView(it).apply {
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String) {
                    super.onPageFinished(view, url)
                    saveUrl(url)
                }
            }
            loadUrl(state.url)
        }
    })
}


