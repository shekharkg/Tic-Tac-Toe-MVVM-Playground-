package com.shekharkg.mvvmplayground.modals

/**
 * Created by shekhar on 2019-07-19.
 * Tricog Health Services Pvt Ltd © 2019 | All rights reserved
 */
data class Cell(var player: Player?) {
    fun isEmpty() = player == null || player?.value.isNullOrEmpty()
}