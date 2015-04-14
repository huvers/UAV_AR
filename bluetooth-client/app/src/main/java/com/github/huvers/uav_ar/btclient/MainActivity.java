package com.github.huvers.uav_ar.btclient;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.github.RoboVision.R;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class MainActivity extends Activity implements BluetoothServerSelector.ConnectedCallback
{
    private static final String TAG = "BtClient-MainActivity";

    private static final UUID serviceId = UUID.fromString("90db942b-6794-44a7-8bf5-6e97ed3f0048");

    private BluetoothClient.ConnectionThread mConnection;
    private BluetoothServerSelector mServerSelector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mServerSelector = new BluetoothServerSelector(this, serviceId, this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ((FrameLayout) findViewById(R.id.frame)).addView(mServerSelector);
    }

    @Override
    public void connected(final BluetoothClient.ConnectionThread connection) {
        Log.d(TAG, "connected");

        this.mConnection = connection;
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mConnection != null) {
            ByteBuffer buffer = ByteBuffer.allocate(24).order(ByteOrder.LITTLE_ENDIAN);

            buffer.putDouble(666);
            buffer.putDouble(666);
            buffer.putDouble(666);

            try {
                mConnection.write(buffer.array());
            }
            catch (IOException e) {
                Log.d(TAG, "error writing end signal");
            }
        }
    }
}
