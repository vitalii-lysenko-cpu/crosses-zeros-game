package com.example.crosses_zeros.ui.components

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.crosses_zeros.R

@Composable
fun Sign(
    painter: Painter,
    alpha: Float,
) {
    SignImpl(
        painter = painter,
        alpha = alpha
    )
}

@Composable
fun SignImpl(
    painter: Painter,
    alpha: Float,
) {
    Icon(
        painter = painter,
        contentDescription = null,
        tint = colorResource(id = R.color.sign_color),
        modifier = Modifier.alpha(alpha)
    )
}

@Preview
@Composable
fun SingPreview() {
    Sign(
        painter = painterResource(id = R.drawable.ic_sing_cross),
        alpha = 1f
    )
}