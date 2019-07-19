package com.shekharkg.mvvmplayground.activities


import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.shekharkg.mvvmplayground.R
import com.shekharkg.mvvmplayground.databinding.ActivityMainBinding
import com.shekharkg.mvvmplayground.viewmodal.GameViewModel
import com.shekharkg.mvvmplayground.modals.Player
import com.shekharkg.mvvmplayground.views.GameBeginDialog
import com.shekharkg.mvvmplayground.views.GameEndDialog

/**
 * Created by shekhar on 2019-07-19.
 * Tricog Health Services Pvt Ltd © 2019 | All rights reserved
 */
class MainActivity : AppCompatActivity() {
    private var gameViewModel: GameViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        promptForPlayers()
    }

    fun promptForPlayers() {
        val dialog = GameBeginDialog.newInstance(this)
        dialog.isCancelable = false
        dialog.show(supportFragmentManager, GAME_BEGIN_DIALOG_TAG)
    }

    fun onPlayersSet(player1: String, player2: String) {
        initDataBinding(player1, player2)
    }

    private fun initDataBinding(player1: String, player2: String) {
        val activityGameBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        gameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        gameViewModel!!.init(player1, player2)
        activityGameBinding.gameViewModel = gameViewModel
        setUpOnGameEndListener()
    }

    private fun setUpOnGameEndListener() {
        gameViewModel!!.winner.observe(this, Observer<Player> { this.onGameWinnerChanged(it) })
    }

    @VisibleForTesting
    fun onGameWinnerChanged(winner: Player?) {
        val winnerName = if (winner == null || winner.name.isEmpty()) NO_WINNER else winner.name
        val dialog = GameEndDialog.newInstance(this, winnerName)
        dialog.isCancelable = false
        dialog.show(supportFragmentManager, GAME_END_DIALOG_TAG)
    }

    companion object {

        private val GAME_BEGIN_DIALOG_TAG = "game_dialog_tag"
        private val GAME_END_DIALOG_TAG = "game_end_dialog_tag"
        private val NO_WINNER = "No one"
    }
}