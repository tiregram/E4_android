package com.tiregram.glove.bluetoothglove;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.squareup.otto.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

public class GloveConnectTo extends DrawerActivity {


    public static class MainThreadBus extends Bus {

        private final Handler mHandler = new Handler(Looper.getMainLooper());


        public MainThreadBus(ThreadEnforcer a){
            super(a);
        }

        @Override
        public void post(final Object event) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                super.post(event);
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        MainThreadBus.super.post(event);
                    }
                });
            }
        }
    }

    static BltReader reader;

    private boolean isAGlove = false;

    private BluetoothSocket mBTSocket;
    private BluetoothDevice mDevice;
    private ProgressDialog progressDialog;
    private int mMaxChars = 1000;
    ConnectThread ct;
    public final static Bus bus = new MainThreadBus(ThreadEnforcer.ANY);

    private UUID mDeviceUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private TextView mTxtReceive, tv_state;
    private boolean mIsUserInitiatedDisconnect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDrawerContentView(R.layout.activity_glove_connect_to);

        bus.register(this);

        Log.e("start","thread");

        Intent intent = getIntent();

        Intent i = this.getIntent();
        System.out.println("name:" + i.getStringExtra("name"));
        System.out.println("addr:" + i.getStringExtra("addr"));


    }

    @Override
    protected void onStart() {
        super.onStart();

        isAGlove = false;

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this.getApplicationContext(), "NO BLT", Toast.LENGTH_SHORT).show();
        }


        Intent i = this.getIntent();

        mTxtReceive = (TextView) this.findViewById(R.id.text_all);

        TextView tv_name = (TextView) this.findViewById(R.id.ConnectTo_name);
        TextView tv_addr = (TextView) this.findViewById(R.id.ConnectTo_addr);
        tv_state = (TextView) this.findViewById(R.id.ConnectTo_state);

        tv_name.setText(i.getStringExtra("name"));
        tv_addr.setText(i.getStringExtra("addr"));


        if (reader != null) {
            tv_state.setText("connected");
            return;

        }
        if (i.getStringExtra("addr") == null) {
            //tv_state.setText("erreur");
            return;

        }


        // TODO: a better test later with respond

        if (i.getStringExtra("addr").equals("20:16:05:25:21:93")) {
            this.isAGlove = true;
            tv_state.setText("try Connection ...");
        } else {

            Toast.makeText(this.getApplicationContext(), "This is not a glove", Toast.LENGTH_SHORT).show();
            isAGlove = false;
            tv_state.setText("is not glove");
            return;
        }


        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        BluetoothDevice btlDevCible = null;

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getAddress().equals(i.getStringExtra("addr"))) {
                    btlDevCible = device;
                    break;
                }

            }
        }


        ct = new ConnectThread(btlDevCible);
        ct.start();


    }


    private boolean mIsBluetoothConnected = false;

    @Override
    protected void onPause() {
        //    if (mBTSocket != null && mIsBluetoothConnected) {
        // new DisConnectBT().execute();
        //  }
        Log.d("BLT", "Paused");
        super.onPause();
    }

    @Override
    protected void onResume() {
        //  if (mBTSocket == null || !mIsBluetoothConnected) {
        //new ConnectBT().execute();
        //   }
        Log.d("BLT", "Resumed");
        super.onResume();
    }


    @Override
    protected void onStop() {
        Log.d("BLT", "Stopped");
        super.onStop();

    }

    protected void onDestroy() {
        Log.d("BLT", "destroy");
        if (reader != null)
            reader.stop();
        reader = null;

        super.onDestroy();


    }


    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                tmp = device.createRfcommSocketToServiceRecord(mDeviceUUID);
            } catch (IOException e) {
                Log.e("BLT", "Socket's create() method failed", e);
            }
            Log.e("BLT", "SUCESS SOCKET");
            mmSocket = tmp;
            mBTSocket = tmp;
        }

        public void run() {
            tv_state.setText("connected");

            Log.e("BLT", "try connection.....");

            try {
                mmSocket.connect();
            } catch (IOException connectException) {
                connectException.printStackTrace();
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.e("BLT", "Could not close the client socket", closeException);
                }
                return;
            }

            Log.e("BLT", "connected");
            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            manageMyConnectedSocket(mmSocket);
        }


        public void manageMyConnectedSocket(BluetoothSocket mmSocket) {
            // TextView tx = (TextView) findViewById(R.id.ConnectTo_state);
            Log.e("BLT", "connected");
            //tx.setText("connected");
            //  tv_state.setText("connected");
            BltReader.Configure(mmSocket);
            BltReader.getInstance();


        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e("BLT", "Could not close the client socket", e);
            }
        }
    }

    public void interpreteLine(final String a){

        mTxtReceive.post(new Runnable() {
            @Override
            public void run() {
                mTxtReceive.append(a);

                int txtLength = mTxtReceive.getEditableText().length();
                if(txtLength > mMaxChars){
                    mTxtReceive.getEditableText().delete(0, txtLength - mMaxChars);
                }



            }
        });

    }

    @Subscribe
    public void answerAvailable(final AnswerLigne event) {

        mTxtReceive.post(new Runnable() {
            @Override
            public void run() {
                mTxtReceive.append(event.ligne+"\n");

                int txtLength = mTxtReceive.getEditableText().length();
                if(txtLength > mMaxChars){
                    mTxtReceive.getEditableText().delete(0, txtLength - mMaxChars);
                }

            }
        });

    }

}


