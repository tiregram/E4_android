package com.tiregram.glove.bluetoothglove;

import android.util.Log;

/**
 * Created by ruhtra on 3/5/17.
 */

public class Poster {

    public static void post(String l)
    {

        if (! AnswerAvailableEvent.valid(l))
        {
            Log.e("poster","not valid");
            return;
        }

        Log.e("poster","valid");

        switch (l.charAt(0))
        {
            case 'r':
            case 'R':
                Log.e("poster","is a gyro");

                AnswerGyro ag = new AnswerGyro(l);
                GloveConnectTo.bus.post(ag);

                break;
            case 'F':
            case 'f':
                Log.e("poster","is a flex");

                AnswerFlex af = new AnswerFlex(l);
                GloveConnectTo.bus.post(af);
                break;
            case 'a':
            case 'A':
                Log.e("poster","is a Ace");

                AnswerAce ac = new AnswerAce(l);
                GloveConnectTo.bus.post(ac);
                break;
            default:

                Log.e("poster","no postman |"+l+"|");

        }


        AnswerLigne al = new AnswerLigne(l);
        GloveConnectTo.bus.post(al);


    }
}
