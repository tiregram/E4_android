package com.tiregram.glove.bluetoothglove;

/**
 * Created by ruhtra on 3/5/17.
 */

public class AnswerFlex extends AnswerAvailableEvent {

    public int n;
    float v;

    public AnswerFlex(String i) {
        super("f");
         String[] word = i.split("\t");

        n = Integer.parseInt(word[1]);
        v = Float.parseFloat(word[2]);
    }
}
