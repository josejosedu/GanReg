package com.example.a6000832.ganreg.Auxiliares;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Jose Eduardo Martin.
 */

//Esta clase es para detectar el sentido de deslizamiento del dedo sobre la pantalla y, sobreescribiendo los 4 últimos métodos, realizar las operaciones que queramos

public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    //Detecto el punto inicial y el punto final, para saber la dirección y en función de ello, se llama a un metodo o a otro
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() < e2.getX()) {
            onSwipeRight();
        }

        if (e1.getX() > e2.getX()) {
            onSwipeLeft();
        }

        if (e1.getY() < e2.getY()) {
            onSwipeBottom();
        }

        if (e1.getY() > e2.getY()) {
            onSwipeTop();
        }
        return true;
    }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }
}