package com.tiregram.glove.bluetoothglove;

import android.bluetooth.BluetoothDevice;

/**
 * Created by ruhtra on 1/23/17.
 */

public class MyBluetoothDevice {

    public MyBluetoothDevice()
    {
        this.name = "unknow";
        this.addr = "00:00:00:00:00";
    }

    public MyBluetoothDevice(BluetoothDevice b)
    {
        this.name = b.getName();
        this.addr = b.getAddress();
    }

    public MyBluetoothDevice(String name, String  addr)
    {
        this.name = name;
        this.addr = addr;
    }


    public String getName() {
        return name;
    }

    public String getAddr() {
        return addr;
    }

    public String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String addr;
    private int devices;


}
