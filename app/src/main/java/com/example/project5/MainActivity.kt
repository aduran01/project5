package com.example.project5

import android.media.SoundPool
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Timer

/**
 * Project 5 CMSC 436: Aldrin Duran and Marvin Alfaro-Orellana
 */
class MainActivity : AppCompatActivity() {
    private lateinit var view : GameView;
    private lateinit var timer : Timer;
    private lateinit var task : GameTimerTask;
    private lateinit var soundPool : SoundPool;
    private var hitNoise = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        soundPool = SoundPool.Builder().build();
        hitNoise = soundPool.load(this, R.raw.hit, 1)


        val model = BrickBreaker(this, soundPool, hitNoise);
        view = GameView(this, model);
        setContentView(view);

        timer = Timer();
        task = GameTimerTask(model, view);
        timer.schedule(task, 0, 16);
    }


}