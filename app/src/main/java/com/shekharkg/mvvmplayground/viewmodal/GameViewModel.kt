package com.shekharkg.mvvmplayground.viewmodal

import androidx.databinding.ObservableArrayMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shekharkg.mvvmplayground.modals.Cell
import com.shekharkg.mvvmplayground.modals.Game
import com.shekharkg.mvvmplayground.modals.Player

import java.util.Objects

/**
 * Created by shekhar on 2019-07-19.
 * Tricog Health Services Pvt Ltd © 2019 | All rights reserved
 */
class GameViewModel : ViewModel() {

    lateinit var cells: ObservableArrayMap<String, String>
    private var game: Game? = null

    val winner: LiveData<Player>
        get() = game!!.winner

    fun init(player1: String, player2: String) {
        game = Game(player1, player2)
        cells = ObservableArrayMap()
    }

    fun onClickedCellAt(row: Int, column: Int) {
        if (Objects.requireNonNull(game!!.cells)[row][column] == null) {
            game!!.cells!![row][column] = Cell(game!!.currentPlayer)
            cells[row.toString() + "" + column] = game!!.currentPlayer.value
            if (game!!.hasGameEnded())
                game!!.reset()
            else
                game!!.switchPlayer()
        }
    }
}
