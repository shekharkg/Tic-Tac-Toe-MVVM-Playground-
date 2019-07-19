package com.shekharkg.mvvmplayground.modals

import androidx.databinding.ObservableArrayMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData


/**
 * Created by shekhar on 2019-07-19.
 * Tricog Health Services Pvt Ltd © 2019 | All rights reserved
 */
class GameViewModel(playerOne: String, playerTwo: String) : ViewModel() {

    private val cells = ObservableArrayMap<String, String>()
    private var game: Game = Game(playerOne, playerTwo)


    private fun onClickedCellAt(row: Int, column: Int) {
        if (game.cells?.get(row)?.get(column) == null) {
            game.cells?.get(row)!![column] = Cell(game.currentPlayer)

            cells["$row, $column"] = game.currentPlayer.value

            if (game.hasGameEnded())
                game.reset()
            else
                game.switchPlayer()
        }
    }


    fun getWinner(): LiveData<Player> {
        return game.winner
    }
}