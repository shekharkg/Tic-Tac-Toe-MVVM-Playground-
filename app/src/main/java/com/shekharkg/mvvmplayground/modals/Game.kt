package com.shekharkg.mvvmplayground.modals

import android.util.Log
import androidx.lifecycle.MutableLiveData


/**
 * Created by shekhar on 2019-07-19.
 * Tricog Health Services Pvt Ltd © 2019 | All rights reserved
 */
class Game(playerOne: String, playerTwo: String) {

    private val TAG = Game::class.java.simpleName
    private val BOARD_SIZE = 3

    var player1: Player
    var player2: Player
    var currentPlayer: Player

    var cells: Array<Array<Cell>>? = null
    var winner = MutableLiveData<Player>()

    init {
        cells = arrayOf()
        player1 = Player(playerOne, "X")
        player2 = Player(playerTwo, "O")
        currentPlayer = player1
    }

    fun switchPlayer() {
        currentPlayer = if (currentPlayer == player1) player2 else player1
    }

    fun hasGameEnded(): Boolean {
        if (hasThreeSameHorizontalCells() || hasThreeSameVerticalCells() || hasThreeSameDiagonalCells()) {
            winner.setValue(currentPlayer)
            return true
        }

        if (isBoardFull()) {
            winner.setValue(null)
            return true
        }

        return false
    }

    private fun hasThreeSameHorizontalCells(): Boolean {
        try {
            for (i in 0 until BOARD_SIZE)
                if (areEqual(cells?.get(i)?.get(0), cells?.get(i)?.get(1), cells?.get(i)?.get(2)))
                    return true

            return false
        } catch (e: NullPointerException) {
            Log.e(TAG, e.message)
            return false
        }

    }

    private fun hasThreeSameVerticalCells(): Boolean {
        try {
            for (i in 0 until BOARD_SIZE)
                if (areEqual(cells?.get(0)?.get(i), cells?.get(1)?.get(i), cells?.get(2)?.get(i)))
                    return true
            return false
        } catch (e: NullPointerException) {
            Log.e(TAG, e.message)
            return false
        }

    }


    private fun hasThreeSameDiagonalCells(): Boolean {
        return try {
            areEqual(cells?.get(0)?.get(0), cells?.get(1)?.get(1), cells?.get(2)?.get(2))
                    || areEqual(
                cells?.get(0)?.get(2), cells?.get(1)?.get(1), cells?.get(2)?.get(0)
            )
        } catch (e: NullPointerException) {
            Log.e(TAG, e.message)
            false
        }

    }

    private fun isBoardFull(): Boolean {
        for (row in cells!!)
            for (cell in row)
                if (cell.isEmpty())
                    return false
        return true
    }

    /**
     * 2 cells are equal if:
     * - Both are not null
     * - Both have non null values
     * - Both have equal values
     * */
    private fun areEqual(vararg cells: Cell?): Boolean {

        if (cells.isNullOrEmpty())
            return false

        for (cell in cells)
            if (cell == null || cell.player?.value.isNullOrEmpty())
                return false

        val comparisonBase = cells[0]
        for (i in 1 until cells.size)
            if (comparisonBase?.player?.value != cells[i]?.player?.value)
                return false

        return true
    }
}