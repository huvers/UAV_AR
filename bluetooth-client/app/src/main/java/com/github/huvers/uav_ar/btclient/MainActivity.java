package com.github.huvers.uav_ar.btclient;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private Button mSendButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mServerSelector = new BluetoothServerSelector(this, serviceId, this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ((FrameLayout) findViewById(R.id.frame)).addView(mServerSelector);

        mSendButton = new Button(this);
        mSendButton.setText("Send message");
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConnection != null) {
                    try {
                        mConnection.write("Hey".getBytes());
                    }
                    catch (IOException e) {
                        Log.d(TAG, "error writing message");
                    }
                }
            }
        });
    }

    @Override
    public void connected(final BluetoothClient.ConnectionThread connection) {
        Log.d(TAG, "connected");

        this.mConnection = connection;

        mServerSelector.setVisibility(View.INVISIBLE);
        ((FrameLayout) findViewById(R.id.frame)).removeAllViews();
        ((FrameLayout) findViewById(R.id.frame)).addView(mSendButton);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (this.isFinishing() && mConnection != null) {
            try {
                mConnection.write("kthanxbai".getBytes());
            }
            catch (IOException e) {
                Log.d(TAG, "error writing end signal");
            }
        }
    }
}
