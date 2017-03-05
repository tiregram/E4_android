package com.tiregram.glove.bluetoothglove;

/**
 * Created by ruhtra on 3/5/17.
 */

public class AnswerLigne extends AnswerAvailableEvent {
    public String ligne;

    public AnswerLigne(String i) {
        super("L");
        ligne = i;
    }
}
