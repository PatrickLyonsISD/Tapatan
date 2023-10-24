package com.example.Tapatan.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    var buttons = Array(3) { arrayOfNulls<String>(3) }
    var currentPlayer = mutableStateOf("Black")
    var roundCount = 0
    var gameEnded = mutableStateOf(false)
    var winner = mutableStateOf<String?>(null)
    var gamePhase = mutableStateOf("placing")
    var selectedPiece = mutableStateOf<Pair<Int, Int>?>(null)

    fun onButtonClick(row: Int, col: Int) {
        when (gamePhase.value) {
            "placing" -> handlePlacingPhase(row, col)
            "moving" -> handleMovingPhase(row, col)
        }
    }

    private fun handlePlacingPhase(row: Int, col: Int) {
        if (buttons[row][col] == null && !gameEnded.value) {
            buttons[row][col] = currentPlayer.value
            roundCount++
            if (checkForWin(row, col)) {
                gameEnded.value = true
                winner.value = currentPlayer.value
            } else if (roundCount == 6) {
                gamePhase.value = "moving"
            }
            changePlayer()
        }
    }

    private fun handleMovingPhase(row: Int, col: Int) {
        selectedPiece.value?.let { (selectedRow, selectedCol) ->
            if (buttons[row][col] == null && isAdjacent(selectedRow, selectedCol, row, col)) {
                buttons[selectedRow][selectedCol] = null
                buttons[row][col] = currentPlayer.value
                if (checkForWin(row, col)) {
                    gameEnded.value = true
                    winner.value = currentPlayer.value
                }
                selectedPiece.value = null
                changePlayer()
            }
        } ?: run {
            if (buttons[row][col] == currentPlayer.value) {
                selectedPiece.value = Pair(row, col)
            }
        }
    }

    private fun isAdjacent(row1: Int, col1: Int, row2: Int, col2: Int): Boolean {
        return Math.abs(row1 - row2) <= 1 && Math.abs(col1 - col2) <= 1 && !(row1 == row2 && col1 == col2)
    }

    private fun changePlayer() {
        if (!gameEnded.value) {
            currentPlayer.value = if (currentPlayer.value == "Black") "White" else "Black"
        }
    }

    private fun checkForWin(row: Int, col: Int): Boolean {
        // Existing checkForWin implementation
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
        gamePhase.value = "placing"
        selectedPiece.value = null
    }
}
