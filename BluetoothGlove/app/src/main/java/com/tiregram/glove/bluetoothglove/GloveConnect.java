package com.tiregram.glove.bluetoothglove;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView.OnItemClickListener;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static android.content.ContentValues.TAG;
import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class GloveConnect extends Activity {

    BluetoothAdapter mBluetoothAdapter;
    UUID my_UUID;
    BluetoothAdaptateur adapter;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                System.out.println("FOUND " + deviceName + " WITH " + deviceHardwareAddress);
            }
        }


    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
       // mBluetoothAdapter.cancelDiscovery();
       // unregisterReceiver(mReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glove_connect);

        // Get ListView object from xml
        ListView listView = (ListView) findViewById(R.id.list_pair);




        ArrayList<MyBluetoothDevice> ar  = new ArrayList<MyBluetoothDevice>();

        adapter = new BluetoothAdaptateur(this,
                R.layout.bluetooth_device,
                R.id.item_blue,
                ar
                );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                    // selected item
                        //String product = ((TextView) view).getText().toString();

                // Launching new Activity on selecting single List Item
                Intent i = new Intent(getApplicationContext(), GloveConnectTo.class);
                // sending data to new activit
                i.putExtra("product", "lapin");
                startActivity(i);

            }
        });

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        PackageManager pm = this.getPackageManager();
        boolean hasBluetooth = pm.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH);

        if (!hasBluetooth) {
            System.out.println("BLUETOOTH IS NOT SUPPORT by pm");
            return;

        }


        if (mBluetoothAdapter == null) {
            System.out.println("BLUETOOTH IS NOT SUPPORT");
            return;
        } else {
            System.out.println("BLUETOOTH IS SUPPORT");
        }

        if (!mBluetoothAdapter.isEnabled()) {
            System.out.println("try to ENABLE BLUETOOTH");
            mBluetoothAdapter.enable();
        } else {
            System.out.println("enable is BLUETOOTH");
        }


        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();


        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                adapter.add(new MyBluetoothDevice(device));
            }
        }


        boolean disscoveredstart = mBluetoothAdapter.startDiscovery();
        if (disscoveredstart) {
            System.out.println("BLUETOOTH DISCOVERY start");
        } else {
            System.out.println("BLUETOOTH DISCOVERY IS off");
        }



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
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                DeviceUuidFactory a = new DeviceUuidFactory(getBaseContext());

                tmp = device.createRfcommSocketToServiceRecord(a.getDeviceUuid());
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Socket's create() method failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            mBluetoothAdapter.cancelDiscovery();

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.e(TAG, "Could not close the client socket", closeException);
                }
                connectException.printStackTrace();
                return;
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            manageMyConnectedSocket(mmSocket);
        }



        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the client socket", e);
            }
        }

        public void manageMyConnectedSocket(BluetoothSocket mmSocket)
        {
            InputStream is = null;
            try {
                is = mmSocket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            while(true) {

                try {
                    if (is.available() > 100) {
                        int a = is.read();
                        System.out.print(a);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}