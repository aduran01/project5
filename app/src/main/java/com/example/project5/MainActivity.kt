package com.example.project5

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Timer

class MainActivity : AppCompatActivity() {
    //needs to be global
    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //we need some other way to set content view

    }

    fun updateModel( ) {
        // move duck
        gameView
    }

    fun updateView( ) {
        gameView.postInvalidate()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        buildViewByCode( )
    }

    fun buildViewByCode( ) {
        var width : Int = resources.displayMetrics.widthPixels
        var height : Int = resources.displayMetrics.heightPixels
        var rectangle : Rect = Rect( 0, 0, 0, 0 )
        window.decorView.getWindowVisibleDisplayFrame( rectangle )
        Log.w( "MainActivity", "width = " + width )
        Log.w( "MainActivity", "height = " + height )
        Log.w( "MainActivity", "status bar height = " + rectangle.top )
        var statusBarHeight : Int = rectangle.top

        gameView = GameView(this, width, height - statusBarHeight)
        setContentView( gameView )

        var timer : Timer = Timer( )
       // var task : GameTimerTask = GameTimerTask( this )
       // timer.schedule( task, 0, 100 )
    }
}