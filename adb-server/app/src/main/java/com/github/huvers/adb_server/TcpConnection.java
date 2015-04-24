package com.github.huvers.adb_server;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.github.huvers.adb_server.gen.FrameProtos.Frame;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class TcpConnection implements Runnable {
    private static final String TAG = "AR_ADB_SERVER";
    private static final int TIMEOUT = 30;

    private String connectionStatus = null;
    private Handler mHandler;
    private ServerSocket server = null;
    private Context context;
    private Socket client = null;
    private List<FrameListener> mFrameListeners = new ArrayList<FrameListener>();

    public TcpConnection(Context context) {
        this.context = context;
        mHandler = new Handler();
    }

    public void addFrameListener(FrameListener listener) {
       mFrameListeners.add(listener);
    }

    public interface FrameListener {
        public void onFrameReceived(Mat frame);
    }

    @Override
    public void run() {
        // initialize server socket
        try {
            Log.d(TAG, "Initializing server");
            server = new ServerSocket(38300);
            server.setSoTimeout(TIMEOUT * 1000);
        } catch (IOException e) {
            Log.d(TAG, "" + e.getStackTrace());
        }

        //attempt to accept a connection
        try{
            Log.d(TAG, "Waiting for connection");
            client = server.accept();

            Thread readThread = new Thread(readFromClient);
            readThread.setPriority(Thread.MAX_PRIORITY);
            readThread.start();
        }
        catch (SocketTimeoutException e) {
            connectionStatus = "Connection timed out";
            mHandler.post(showConnectionStatus);
            try {
                server.close();
            } catch (IOException e1) {
                Log.d(TAG, "" + e1.getStackTrace());
            }
        }
        catch (IOException e) {
            Log.d(TAG, "" + e);
        }
        if (client != null) {
            try{
                connectionStatus = "Connection successful!";
                Log.d(TAG, connectionStatus);
                mHandler.post(showConnectionStatus);
            }
            catch(Exception e) {
                Log.d(TAG, "" + e.getStackTrace());
            }
        }
    }

    private Runnable readFromClient = new Runnable() {
        @Override
        public void run() {
            try {
                Log.d(TAG, "Reading from client");
                while (client.isConnected()) {
                    Frame frameMessage = Frame.parseDelimitedFrom(client.getInputStream());
                    if (frameMessage == null) {
                        break;
                    }
                    Log.d(TAG, "Received: " + frameMessage.getSerializedSize() + " bytes");
                    Mat frame = new Mat(frameMessage.getRows(), frameMessage.getCols(),
                            CvType.CV_8UC1);
                    frame.put(0, 0, frameMessage.getData().toByteArray());
                    for (FrameListener listener : mFrameListeners) {
                        listener.onFrameReceived(frame);
                    }
                }
                closeAll();
                Log.d(TAG, "Finished reading");
            }
            catch (IOException e) {
                Log.d(TAG, "" + e.getStackTrace());
            }
        }
    };

    public void closeAll() {
        try {
            Log.d(TAG, "Closing sockets");
            client.close();
            server.close();
        } catch (IOException e) {
            Log.d(TAG, "" + e.getStackTrace());
        }
    }

    private Runnable showConnectionStatus = new Runnable() {
        public void run() {
            try {
                Toast.makeText(context, connectionStatus, Toast.LENGTH_SHORT).show();
            }
            catch(Exception e) {
                Log.d(TAG, "" + e.getStackTrace());
            }
        }
    };
}