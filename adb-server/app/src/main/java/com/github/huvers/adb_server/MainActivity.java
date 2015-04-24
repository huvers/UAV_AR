package com.github.huvers.adb_server;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    private static final String TAG = "AR_ADB_CLIENT";

    private TcpConnection server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        server = new TcpConnection(this);
        new Thread(server).start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isFinishing()) {
            server.closeAll();
        }
    }
}
