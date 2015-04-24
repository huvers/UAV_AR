package com.github.huvers.adb_server;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

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
                mBitmap = Bitmap.createBitmap(frame.cols(), frame.rows(), Bitmap.Config.ARGB_8888);
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
