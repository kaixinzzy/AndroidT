package com.zzy.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class StartService extends Service {

    public StartService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
