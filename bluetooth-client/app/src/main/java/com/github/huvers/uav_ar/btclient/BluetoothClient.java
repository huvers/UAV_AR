package com.github.huvers.uav_ar.btclient;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 * Represents a Bluetooth client that can connect to a server running a specified service
 * Blocks while connecting, then provides thread for writing bytes to server
 */
public class BluetoothClient
{
    private final UUID serviceId;
    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public BluetoothClient(UUID serverServiceId) {
        serviceId = serverServiceId;
    }

    public ConnectionThread connectToServer(BluetoothDevice server) throws IOException, InterruptedException, ExecutionException {
        Future<BluetoothSocket> future = Executors.newSingleThreadExecutor().submit(new ClientConnector(server));
        BluetoothSocket socket = future.get();

        return new ConnectionThread(socket);
    }

    public Set<BluetoothDevice> getPairedDevices() {
        return bluetoothAdapter.getBondedDevices();
    }

    private class ClientConnector implements Callable<BluetoothSocket>
    {
        public final BluetoothSocket socket;

        public ClientConnector(BluetoothDevice device) throws IOException {
            socket = device.createRfcommSocketToServiceRecord(serviceId);
        }

        @Override
        public BluetoothSocket call() throws IOException {
            bluetoothAdapter.cancelDiscovery();

            while(true) {
                socket.connect();

                if (socket != null) {
                    return socket;
                }
            }
        }
    }

    public class ConnectionThread extends Thread
    {
        private final BluetoothSocket socket;
        private final OutputStream out;

        public ConnectionThread(BluetoothSocket socket) throws IOException {
            this.socket = socket;
            out = socket.getOutputStream();
        }

        public void write(byte[] bytes) throws IOException {
            out.write(bytes);
        }

        public void close() throws IOException {
            socket.close();
        }
    }
}