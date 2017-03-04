package com.tiregram.glove.bluetoothglove;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

public class GloveConnectTo extends Activity {


    private  boolean isAGlove = false;

    private BluetoothSocket mBTSocket;
    private BluetoothDevice mDevice;
    private ProgressDialog progressDialog;
    private int mMaxChars = 1000;
    ConnectThread ct;

    private UUID mDeviceUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private  TextView mTxtReceive;
    private boolean mIsUserInitiatedDisconnect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glove_connect_to);

        Intent intent = getIntent();

        Intent i = this.getIntent();
        System.out.println("name:"+i.getStringExtra("name"));
        System.out.println("addr:"+i.getStringExtra("addr"));



    }

    @Override
    protected  void onStart()    {
        super.onStart();

        isAGlove = false;

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this.getApplicationContext(),"NO BLT",Toast.LENGTH_SHORT).show();
        }




        Intent i = this.getIntent();

        TextView mTxtReceive = (TextView) this.findViewById(R.id.text_all);

        TextView tv_name = (TextView) this.findViewById(R.id.ConnectTo_name);
        TextView tv_addr = (TextView) this.findViewById(R.id.ConnectTo_addr);
        TextView tv_state = (TextView)this.findViewById(R.id.ConnectTo_state);

        tv_name.setText(i.getStringExtra("name"));
        tv_addr.setText(i.getStringExtra("addr"));


        // TODO: a better test later with respond

        if(i.getStringExtra("addr").equals("20:16:05:25:21:93"))
        {
            this.isAGlove = true;
            tv_state.setText("try Connection ...");
        }
        else
        {

            Toast.makeText(this.getApplicationContext(), "This is not a glove", Toast.LENGTH_SHORT).show();
            isAGlove = false;
            tv_state.setText("is not glove");
            return;
        }


        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        BluetoothDevice btlDevCible = null;

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if(device.getAddress().equals(i.getStringExtra("addr")))
                {
                    btlDevCible = device;
                    break;
                }

            }
        }


        ct = new ConnectThread(btlDevCible);
        ct.run();


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
            ///new ConnectBT().execute();
     //   }
        Log.d("BLT", "Resumed");
        super.onResume();
    }


    @Override
    protected void onStop() {
        Log.d("BLT", "Stopped");
        super.onStop();
    }

    public  void run_text(View v){
        if(isAGlove) {
            Intent i = new Intent(getApplicationContext(), FlexSensorActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(this.getApplicationContext(), "This is not a glove", Toast.LENGTH_SHORT).show();
        }
    }
    public  void run_TRD(View v){
        if(isAGlove) {
            Intent i = new Intent(getApplicationContext(), Renderer.class);
            startActivity(i);
        }else{
            Toast.makeText(this.getApplicationContext(), "This is not a glove", Toast.LENGTH_SHORT).show();
        }

    }
    public  void run_graph_flex(View v){
        if(isAGlove) {
        Intent i = new Intent(getApplicationContext(), FlexSensorActivity.class);
        startActivity(i);
    }else{
        Toast.makeText(this.getApplicationContext(), "This is not a glove", Toast.LENGTH_SHORT).show();
    }
    }
    public  void run_graph_ace(View v){
        if(isAGlove) {
        Intent i = new Intent(getApplicationContext(), AccelerometerActivity.class);
        startActivity(i);
        }else{
            Toast.makeText(this.getApplicationContext(), "This is not a glove", Toast.LENGTH_SHORT).show();
        }
    }
    public  void run_graph_gyro(View v){
        if(isAGlove) {
        Intent i = new Intent(getApplicationContext(), GyroscopeActivity.class);
        startActivity(i);
    }else{
        Toast.makeText(this.getApplicationContext(), "This is not a glove", Toast.LENGTH_SHORT).show();
    }
    }
    public  void run_mouse(View v){
        Toast.makeText(this.getApplicationContext(), "This is not a glove", Toast.LENGTH_SHORT).show();
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
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            //mBluetoothAdapter.cancelDiscovery();

            Log.e("BLT", "try connection.....");

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.e("BLT", "Could not close the client socket", closeException);
                }
                return;
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
           manageMyConnectedSocket(mmSocket);
        }


        public void manageMyConnectedSocket(BluetoothSocket mmSocket)
        {
            TextView tx = (TextView) findViewById(R.id.ConnectTo_state);
            Log.e("BLT", "connected");
            tx.setText("connected");

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
}
