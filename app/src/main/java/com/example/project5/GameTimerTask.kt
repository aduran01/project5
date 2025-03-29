package com.example.project5

import java.util.TimerTask

class GameTimerTask(private val model : BrickBreaker,
                    private val view: GameView) : TimerTask() {
    override fun run() {

        model.updateBallPos();
        view.postInvalidate();
    }

}