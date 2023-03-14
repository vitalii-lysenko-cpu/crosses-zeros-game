package com.example.crosses_zeros.ui.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.crosses_zeros.ui.presentation.gameboard.GameBoard
import com.example.crosses_zeros.ui.theme.CrossesZerosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrossesZerosTheme {
                GameBoard()
            }
        }
    }
}

