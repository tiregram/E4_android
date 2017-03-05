package com.tiregram.glove.bluetoothglove;


import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class BltReader implements Runnable {

    private static BluetoothSocket mmSocket;
    private boolean bStop = false;
    private Thread t;

    private static BltReader uniq;


    public GyroscopeActivity ga;
    public GloveConnectTo gc;

    public static void Configure(BluetoothSocket so)
    {
        mmSocket = so;
    }

    public static BltReader getInstance()
    {

        if(uniq == null)
            uniq = new BltReader();

        return uniq;

    }

    private BltReader() {
        t = new Thread(this, "Reader BLT");
        t.start();
    }

    public boolean isRunning() {
        return t.isAlive();
    }

    @Override
    public void run() {


        InputStream inputStream;
        String old = "";

        try {
            inputStream = mmSocket.getInputStream();

            while (!bStop) {
                byte[] buffer = new byte[512];

                if (inputStream.available() > 0) {
                    inputStream.read(buffer);
                    int i = 0;

                    for (i = 0; i < buffer.length && buffer[i] != 0; i++) {
                    }


                    final String strInput = new String(buffer, 0, i);

                    String[] lignes = (old + strInput).split("\n");
                    old = lignes[lignes.length-1];

                    for(int x = 0 ; x < lignes.length -1 ; x++) {
                        Log.d("mess", "x = " + x + " -> " + lignes[x]);
                        Poster.post(lignes[x]);
                    }



                   // GloveConnectTo.bus.post(new AnswerAvailableEvent(lignes));


                }
                Thread.sleep(1000/60);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void stop() {
        bStop = true;
    }

}

