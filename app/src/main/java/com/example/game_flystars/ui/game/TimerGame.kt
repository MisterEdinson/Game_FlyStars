package com.example.game_flystars.ui.game

import android.os.Handler
import android.os.SystemClock
import androidx.lifecycle.MutableLiveData

class TimerGame {

    val time : MutableLiveData<String> = MutableLiveData()
    private var running = false
    private var elapsedTime = 0L
}