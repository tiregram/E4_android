package com.tiregram.glove.bluetoothglove;

/**
 * Created by ruhtra on 3/5/17.
 */

public class AnswerFlex extends AnswerAvailableEvent {

    public int n, value;

    public AnswerFlex(String i) {
        super("f");
         String[] word = i.split("\t");

        n = Integer.parseInt(word[1]);
        value = 10;//Integer.parseInt(word[2]);
    }
}
