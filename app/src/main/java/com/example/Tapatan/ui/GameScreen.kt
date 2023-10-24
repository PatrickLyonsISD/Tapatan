
package com.example.Tapatan.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {
    val currentPlayer by remember(gameViewModel) { derivedStateOf { gameViewModel.currentPlayer } }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = "Turn: $currentPlayer",
                fontSize = 40.sp
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            for (row in 0 until 3) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (col in 0 until 3) {
                        MyButton(
                            text = gameViewModel.buttons[row][col] ?: "",
                            row = row,
                            col = col,
                            gameViewModel = gameViewModel,
                            onClick = {
                                gameViewModel.onButtonClick(row, col)
                            })
                    }
                }
            }
        }

        MyButton("Reset Game", row = 0, col = 0, gameViewModel, onClick = { gameViewModel.resetGame() })

    }
}

@Composable
fun MyButton(text: String, row: Int, col: Int, gameViewModel: GameViewModel, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text)
    }
}

@Preview
@Composable
fun MyComposableLayoutPreview() {
    GameScreen(GameViewModel())
}
