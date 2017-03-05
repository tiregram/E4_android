package com.tiregram.glove.bluetoothglove;

import android.util.Log;

/**
 * Created by ruhtra on 3/5/17.
 */

public class AnswerGyro extends AnswerAvailableEvent {

    float x,y,z;


    public AnswerGyro(String lignes) {
        super("G");
        String[] word = lignes.split("\t");

        x  = Float.parseFloat(word[1]);
        y  = Float.parseFloat(word[2]);
        z  = Float.parseFloat(word[3]);
    }
}
