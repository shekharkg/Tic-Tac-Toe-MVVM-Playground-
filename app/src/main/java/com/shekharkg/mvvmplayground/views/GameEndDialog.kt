package com.shekharkg.mvvmplayground.views


import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.shekharkg.mvvmplayground.R
import com.shekharkg.mvvmplayground.activities.MainActivity


class GameEndDialog : DialogFragment() {

    private var rootView: View? = null
    private var activity: MainActivity? = null
    private var winnerName: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        initViews()
        val alertDialog = AlertDialog.Builder(context!!)
            .setView(rootView)
            .setCancelable(false)
            .setPositiveButton(R.string.done) { dialog, which -> onNewGame() }
            .create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setCancelable(false)
        return alertDialog
    }

    private fun initViews() {
        rootView = LayoutInflater.from(context)
            .inflate(R.layout.game_end_dialog, null, false)
        (rootView!!.findViewById<View>(R.id.tv_winner) as TextView).text = winnerName
    }

    private fun onNewGame() {
        dismiss()
        activity!!.promptForPlayers()
    }

    companion object {

        fun newInstance(activity: MainActivity, winnerName: String): GameEndDialog {
            val dialog = GameEndDialog()
            dialog.activity = activity
            dialog.winnerName = winnerName
            return dialog
        }
    }
}
