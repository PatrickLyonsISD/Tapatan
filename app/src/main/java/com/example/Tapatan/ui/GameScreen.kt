package com.example.Tapatan.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {
    val currentPlayer by gameViewModel.currentPlayer
    val buttons = gameViewModel.buttons
    val gameEnded by gameViewModel.gameEnded
    val winner by gameViewModel.winner
    val blackScore by gameViewModel.blackScore
    val whiteScore by gameViewModel.whiteScore

    if (gameEnded && winner != null) {
        AlertDialog(
            onDismissRequest = { gameViewModel.resetGame() },
            title = { Text("Congratulations!") },
            text = { Text("$winner player, you won!") },
            confirmButton = {
                Button(onClick = { gameViewModel.resetGame() }) {
                    Text("OK")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Turn: $currentPlayer",
                fontSize = 40.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                text = "Black: $blackScore, White: $whiteScore",
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

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
                                text = buttons[row][col] ?: "",
                                onClick = { gameViewModel.onButtonClick(row, col) }
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MyButton("Reset Game", onClick = { gameViewModel.resetGame() })
                MyButton("New Game", onClick = { gameViewModel.newGame() })
            }
        }
    }
}

@Composable
fun MyButton(text: String, onClick: () -> Unit, isSelected: Boolean = false) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
    } else {
        MaterialTheme.colorScheme.surface
    }

    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor, contentColor = contentColor),
        modifier = Modifier
            .padding(8.dp)
            .background(backgroundColor, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text)
    }
}


@Preview
@Composable
fun MyComposableLayoutPreview() {
    GameScreen(GameViewModel())
}
