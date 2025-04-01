package com.example.project5

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class GameView(context : Context, private val model : BrickBreaker) : View(context) {

    private val paint = Paint();

    init {
        paint.isAntiAlias = true;
        paint.style = Paint.Style.FILL;
    }

    override fun onDraw(canvas: Canvas) {
        val colors = listOf(Color.RED, Color.GREEN,
            Color.YELLOW , Color.LTGRAY ,Color.BLUE, Color.MAGENTA,
            Color.DKGRAY, Color.CYAN);

        super.onDraw(canvas)

        //create bricks but we need the sizing down first.
        var number = 0;
        //canvas.dra
        for ((i, row) in model.bricks.withIndex()) {

            for((j, brick) in row.withIndex()) {

                //need this to create the color pattern
                //in the ss. Use mod to switch between the two colors
                //at the end of the outer loop add +2 for the
                //next set of colors for that row.
                if(j % 2 == 0) {
                    paint.color = colors[number];
                }
                else {
                    paint.color = colors[number + 1];
                }


                if(brick) {
                    canvas.drawRect(
                        j * model.brickWidth,
                        i * model.brickHeight,
                        (j + 1) * model.brickWidth,
                        (i + 1) * model.brickHeight,
                        paint
                    );
                }

            }

            number += 2;


        }

        paint.color = Color.GRAY;
        canvas.drawRect(
            model.paddleX,
            model.paddleY,
            model.paddleX + model.paddleWidth,
            model.paddleY + model.paddleHeight,
            paint
        );

        canvas.drawCircle(model.ballX, model.ballY, model.ballRadius, paint);

        if(model.gameOver) {
            paint.color = Color.RED;
            paint.textSize = 60f ;
            canvas.drawText("Game Over", 150f, 500f, paint)
            canvas.drawText("Bricks Hit: ${model.score}", 150f, 600f, paint);
            canvas.drawText("Bricks Left: ${model.bricksLeft()}", 150f, 700f, paint);
            canvas.drawText("Personal Best: ${model.personalBest}",  150f, 800f, paint);
            if(model.score == model.personalBest) {
                canvas.drawText("New Personal Best!",  150f, 900f, paint);
            }
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            if(!model.gameStarted && event.action == MotionEvent.ACTION_DOWN) {
                model.gameStarted = true;

            }
        }

        if (event != null) {
            if(event.action == MotionEvent.ACTION_MOVE || event.action == MotionEvent.ACTION_DOWN) {
                model.moveLeftRight(event.x);

            }
        }

        return true
    }

}