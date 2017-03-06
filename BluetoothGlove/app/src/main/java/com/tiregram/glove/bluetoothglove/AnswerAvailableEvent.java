package com.tiregram.glove.bluetoothglove;

import android.util.Log;

/**
 * Created by ruhtra on 3/5/17.
 */
public abstract class AnswerAvailableEvent {

    public static boolean valid(String ligne)
    {
        String [] split = ligne.split("\t");

        if(split.length > 4) {
            Log.e("answer check","not valid size "+ split.length +" > " + 4);
            return false;
        }
        if(!   (split[0].equals("R") ||
                split[0].equals("A") ||
                split[0].equals("F") ||
                split[0].equals("r") ||
                split[0].equals("a") ||
                split[0].equals("f"))
                ) {
            Log.e("answer check","not valid cara "+ split.length +" > " + 4);
            return false;
        }

        if(ligne.contains("nan") || ligne.contains("FIFO")) {
            Log.e("answer check","not valid number "+ split.length +" > " + 4);
            return false;
        }


        return true;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type = "undef";

    public AnswerAvailableEvent(String i) {
        type = i;
    }
}
