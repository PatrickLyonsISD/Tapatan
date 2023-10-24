package com.example.Tapatan.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {
    val currentPlayer by gameViewModel.currentPlayer
    val buttons = gameViewModel.buttons
    val gameEnded by gameViewModel.gameEnded
    val winner by gameViewModel.winner
    val gamePhase by gameViewModel.gamePhase
    val selectedPiece by gameViewModel.selectedPiece

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
            .background(MaterialTheme.colorScheme.primary) // Apply background color here
            .padding(16.dp),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
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
                            text = buttons[row][col] ?: "",
                            onClick = {
                                gameViewModel.onButtonClick(row, col)
                            },
                            isSelected = selectedPiece == Pair(row, col)
                        )
                    }
                }
            }
        }

        MyButton("Reset Game", onClick = { gameViewModel.resetGame() })
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
