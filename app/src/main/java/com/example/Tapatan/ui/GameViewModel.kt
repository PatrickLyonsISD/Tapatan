package com.example.Tapatan.ui



import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel



class GameViewModel : ViewModel() {
    var buttons = Array(3) { arrayOfNulls<String>(3) }
    var currentPlayer = mutableStateOf("Black")
    var roundCount = 0
    var gameEnded = mutableStateOf(false)
    var winner = mutableStateOf<String?>(null)



    fun onButtonClick(row: Int, col: Int) {
        if (buttons[row][col] == null && !gameEnded.value) {
            buttons[row][col] = currentPlayer.value
            roundCount++
            if (checkForWin(row, col)) {
                gameEnded.value = true
                winner.value = currentPlayer.value
            } else if (roundCount == 9) {
                gameEnded.value = true
            } else {
                currentPlayer.value = if (currentPlayer.value == "Black") "White" else "Black"
            }
        }
    }



    private fun checkForWin(row: Int, col: Int): Boolean {
        val player = buttons[row][col]



        // Check for horizontal win
        for (i in 0 until 3) {
            if (buttons[row][i] != player) {
                break
            }
            if (i == 2) {
                return true
            }
        }



        // Check for vertical win
        for (i in 0 until 3) {
            if (buttons[i][col] != player) {
                break
            }
            if (i == 2) {
                return true
            }
        }



        // Check for diagonal win (top-left to bottom-right)
        if (row == col) {
            for (i in 0 until 3) {
                if (buttons[i][i] != player) {
                    break
                }
                if (i == 2) {
                    return true
                }
            }
        }



        // Check for diagonal win (top-right to bottom-left)
        if (row + col == 2) {
            for (i in 0 until 3) {
                if (buttons[i][2 - i] != player) {
                    break
                }
                if (i == 2) {
                    return true
                }
            }
        }



        return false
    }



    fun resetGame() {
        buttons = Array(3) { arrayOfNulls<String>(3) }
        currentPlayer.value = "Black"
        roundCount = 0
        gameEnded.value = false
        winner.value = null
    }
}