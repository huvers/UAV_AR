package com.github.huvers.adb_server;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.huvers.adb_client.gen.ControlProtos.DroneControl.Command;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

public class MainActivity extends Activity {
    private static final String TAG = "AR_ADB_CLIENT";

    private TcpConnection server;
    private ImageView mCameraView;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    Log.i(TAG, "OpenCV loaded successfully");
                    displayFeed();
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        addDroneControlButtonListener(R.id.pitch_forward, Command.PITCH_FORWARD);
        addDroneControlButtonListener(R.id.pitch_backward, Command.PITCH_BACKWARD);
        addDroneControlButtonListener(R.id.roll_left, Command.ROLL_LEFT);
        addDroneControlButtonListener(R.id.roll_right, Command.ROLL_RIGHT);
        addDroneControlButtonListener(R.id.yaw_left, Command.YAW_LEFT);
        addDroneControlButtonListener(R.id.yaw_right, Command.YAW_RIGHT);
        addDroneControlButtonListener(R.id.increase_altitude, Command.INCREASE_ALTITUDE);
        addDroneControlButtonListener(R.id.decrease_altitude, Command.DECREASE_ALTITUDE);
        addDroneControlButtonListener(R.id.takeoff, Command.TAKEOFF);
        addDroneControlButtonListener(R.id.land, Command.LAND);
        addDroneControlButtonListener(R.id.leap, Command.LEAP);
        addDroneControlButtonListener(R.id.picture, Command.PICTURE);
        addDroneControlButtonListener(R.id.emergency, Command.EMERGENCY);
    }



    private void addDroneControlButtonListener(int id, final Command command) {
        final Button button = (Button) findViewById(id);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "Sending command to drone: " + command);
                // TODO: Send data to the server
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isFinishing()) {
            server.closeAll();
        }
    }

    public void displayFeed() {
        setContentView(R.layout.activity_main);
        mCameraView = (ImageView)findViewById(R.id.cameraView);

        server = new TcpConnection(this);
        server.addFrameListener(new TcpConnection.FrameListener() {
            Bitmap mBitmap = null;

            @Override
            public void onFrameReceived(Mat frame) {
                mBitmap = Bitmap.createBitmap(frame.rows(), frame.cols(), Bitmap.Config.RGB_565);
                Utils.matToBitmap(frame, mBitmap);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCameraView.setImageBitmap(mBitmap);
                    }
                });
            }
        });
        new Thread(server).start();
    }
}
