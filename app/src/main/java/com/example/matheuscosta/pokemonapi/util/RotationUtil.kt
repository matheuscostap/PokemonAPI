package com.example.matheuscosta.pokemonapi.util

import android.view.MotionEvent



object RotationUtil {

    //https://stackoverflow.com/questions/3148741/how-to-capture-finger-movement-direction-in-android-phone
    fun getScrollDirection(event: MotionEvent): Directions?{
        var x1: Float = 0f
        var x2: Float = 0f
        var y1: Float = 0f
        var y2: Float = 0f
        var dx: Float = 0f
        var dy: Float = 0f

        when (event.getAction()) {
            MotionEvent.ACTION_DOWN -> {
                x1 = event.getX()
                y1 = event.getY()
            }

            MotionEvent.ACTION_UP -> {
                x2 = event.getX()
                y2 = event.getY()
                dx = x2 - x1
                dy = y2 - y1

                // Use dx and dy to determine the direction of the move
                if (Math.abs(dx) > Math.abs(dy)) {
                    if (dx > 0)
                        return Directions.RIGHT
                    else
                        return Directions.LEFT
                } else {
                    if (dy > 0)
                        return Directions.DOWN
                    else
                        return Directions.UP
                }
            }
        }

        return null
    }
}