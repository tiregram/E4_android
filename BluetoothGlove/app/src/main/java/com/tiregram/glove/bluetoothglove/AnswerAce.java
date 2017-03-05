package com.tiregram.glove.bluetoothglove;

import android.util.Log;

/**
 * Created by ruhtra on 3/5/17.
 */

public class AnswerAce extends AnswerAvailableEvent {

    float x,y,z;

    public AnswerAce(String lignes) {
        super("a");

        String[] word = lignes.split("\t");

        x  = Float.parseFloat(word[1]);
        y  = Float.parseFloat(word[2]);
        z  = Float.parseFloat(word[3]);

    }
}
