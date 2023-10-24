/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.Tapatan.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel



class GameViewModel : ViewModel() {
    var buttons: Array<Array<MutableState<String?>>> = Array(3) { Array(3) { mutableStateOf(null) } }
    var currentPlayer: MutableState<String> = mutableStateOf("Black")
    var roundCount: MutableState<Int> = mutableStateOf(0)
    var gameEnded: MutableState<Boolean> = mutableStateOf(false)



    fun onButtonClick(row: Int, col: Int) {
        if (buttons[row][col].value == null && !gameEnded.value) {
            buttons[row][col].value = currentPlayer.value
            roundCount.value = roundCount.value + 1
            if (checkForWin(row, col)) {
                gameEnded.value = true
            } else if (roundCount.value == 9) {
                gameEnded.value = true
            } else {
                currentPlayer.value = if (currentPlayer.value == "Black") "White" else "Black"
            }
        }
    }



    private fun checkForWin(row: Int, col: Int): Boolean {
        val player = buttons[row][col].value



        // Check for horizontal win
        for (i in 0 until 3) {
            if (buttons[row][i].value != player) {
                break
            }
            if (i == 2) {
                return true
            }
        }



        // Check for vertical win
        for (i in 0 until 3) {
            if (buttons[i][col].value != player) {
                break
            }
            if (i == 2) {
                return true
            }
        }



        // Check for diagonal win (top-left to bottom-right)
        if (row == col) {
            for (i in 0 until 3) {
                if (buttons[i][i].value != player) {
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
                if (buttons[i][2 - i].value != player) {
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
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].value = null
            }
        }
        currentPlayer.value = "Black"
        roundCount.value = 0
        gameEnded.value = false
    }
}