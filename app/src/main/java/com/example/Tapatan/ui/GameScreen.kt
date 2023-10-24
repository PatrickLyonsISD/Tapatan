package com.example.Tapatan.ui



import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {
    val currentPlayer by gameViewModel.currentPlayer
    val buttons = gameViewModel.buttons
    val gameEnded by gameViewModel.gameEnded
    val winner by gameViewModel.winner



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
                            }
                        )
                    }
                }
            }
        }



        MyButton("Reset Game", onClick = { gameViewModel.resetGame() })
    }
}



@Composable
fun MyButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
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