package com.tiregram.glove.bluetoothglove;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BltConnection extends Service {
    public BltConnection() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
