package com.example.project5

import android.content.Context
import android.content.SharedPreferences
import android.media.SoundPool

class BrickBreaker(private val context: Context,
                   private val soundPool: SoundPool,
                   private val hitNoise : Int) {

    val numOfRows = 4;
    val numofCols = 6;
    val bricks = Array(numOfRows) {BooleanArray(numofCols) {true} };

    var widthOfScreen = context.resources.displayMetrics.widthPixels.toFloat();
    var heightOfScreen = context.resources.displayMetrics.heightPixels.toFloat();

    //here we calculate the width of the bricks using the calc width

    var brickWidth = widthOfScreen / numofCols;
    var brickHeight = heightOfScreen / 20;

    var paddleWidth = widthOfScreen / 6;
    var paddleHeight = 25f ;

    var paddleX = (widthOfScreen / 2) - (paddleWidth / 2);
    var paddleY = heightOfScreen - 150f;

    var ballRadius = 25f ;
    var ballX = widthOfScreen / 2;
    var ballY = heightOfScreen / 2;

    //the speed of the ball should be zero until game is started.
    var ballSpeedX = 0f;
    var ballSpeedY = 0f;

    var score = 0;
    var personalBest = 0;

    var gameStarted = false;
    var gameOver = false;
    var once = 0;
    val array = arrayOf(10f, -10f)


    private val preferences : SharedPreferences = context.getSharedPreferences(
        "InGamePref", Context.MODE_PRIVATE);

    init {
        personalBest = preferences.getInt("personalBest", 0);

    }

    fun moveLeftRight(x : Float) {
        paddleX = x - (paddleWidth / 2);
        if(paddleX < 0) {
            paddleX = 0f;
        }
        if(paddleX + paddleWidth > widthOfScreen) {
            paddleX = widthOfScreen - paddleWidth;
        }
    }

    fun updateBallPos() {



        if(!gameStarted || gameOver) {
            return;
        }

        if(gameStarted && once == 0) {
            //this will change whether it goes left or right
            //its random.
            ballSpeedX = array.random()
            ballSpeedY = 10f;
            once++;
        }


        ballX += ballSpeedX;
        ballY += ballSpeedY;

        val bottomOfBall = ballY + ballRadius;

        val leftOfBall = ballX - ballRadius ;

        val rightOfBall = ballX + ballRadius ;

        val leftOfPaddle = paddleX;

        val rightOfPaddle = paddleX + paddleWidth ;

        val topOfPaddle = paddleY;

        val bottomOfPaddle = paddleY + paddleHeight ;

        if(ballX - ballRadius < 0 || ballX + ballRadius > widthOfScreen) {
            ballSpeedX *= -1;
        }

        if(ballY - ballRadius < 0) {
            ballSpeedY *= -1;
        }



        if (bottomOfBall >= topOfPaddle && ballY <= bottomOfPaddle &&
            rightOfBall >= leftOfPaddle && leftOfBall <= rightOfPaddle) {
            //had to add this because the ball was phasing through the paddle
            ballSpeedY *= -1;
            ballY = paddleY - ballRadius;

            //change the pitch everytime play is called, just so
            //the sound doesn't get repetitive
            val randomPitch = (0.8 + Math.random() * 0.4).toFloat()
            soundPool.play(hitNoise, 2f, 2f, 1, 0, randomPitch)
        }

        //ends the game once you get pass the paddle at the bottom of
        //the screen.
        if(ballY + ballRadius > heightOfScreen) {
            gameOver = true;
            if(score > personalBest) {
                personalBest = score;
                preferences.edit().putInt("personalBest", personalBest).apply();
            }
        }

        val row = (ballY / brickHeight).toInt();
        val col = (ballX / brickWidth).toInt();

        if (row in 0 until numOfRows && col in 0 until numofCols
            && bricks[row][col]) {
            bricks[row][col] = false;
            score ++;
            ballSpeedY *= -1;
        }
    }

    fun bricksLeft() : Int {
        return bricks.sumOf { row -> row.count {it} };
    }

}