package com.github.huvers.uav_ar.btclient;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/*
 * Simple UI for connecting to a Bluetooth server
 * Composed of a TextView status console and ListView displaying paired Bluetooth devices
 * Takes the Android activity context, the id of the Bluetooth service, and an implementation of ConnectedCallback
 */
public class BluetoothServerSelector extends LinearLayout
{
    private TextView textView;
    private ListView listView;

    private BluetoothClient bluetoothClient;
    private ConnectedCallback connectionListener;

    public BluetoothServerSelector(Context context, UUID serviceId, ConnectedCallback connectionListener) {
        super(context);

        this.connectionListener = connectionListener;
        bluetoothClient = new BluetoothClient(serviceId);

        // build UI

        textView = new TextView(context);
        listView = new ListView(context);

        textView.setTextSize(18);

        final ArrayAdapter<BluetoothDevice> deviceAdapter = new ArrayAdapter<BluetoothDevice>(context, 0, new ArrayList<BluetoothDevice>()) {
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = new TextView(this.getContext());
                view.setTextSize(20);
                view.setPadding(10, 10, 10, 10);
                view.setText(this.getItem(position).getName());
                return view;
            }
        };

        listView.setAdapter(deviceAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                connectToServer(deviceAdapter.getItem(position));
            }
        });

        this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        this.setOrientation(LinearLayout.VERTICAL);

        this.addView(textView, 0);
        this.addView(listView, 1);

        // display available servers

        Set<BluetoothDevice> pairedDevices = bluetoothClient.getPairedDevices();

        if (!pairedDevices.isEmpty()) {
            for (BluetoothDevice device : pairedDevices) {
                deviceAdapter.add(device);
            }

            textView.setText("Select a Bluetooth server");
        }
        else {
            textView.setText("No paired devices found");
        }
    }

    private void connectToServer(BluetoothDevice server) {
        BluetoothClient.ConnectionThread connection = null;

        try {
            connection = bluetoothClient.connectToServer(server);
        }
        catch (Exception e) {
            textView.setText("Error connecting to server");
        }

        textView.setText("Connected!");

        connectionListener.connected(connection);
    }

    public interface ConnectedCallback
    {
        public void connected(BluetoothClient.ConnectionThread connection);
    }
}
