package com.XiaomiM365Locker.app;


import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.polidea.rxandroidble2.RxBleConnection;

import java.util.List;

public class DeviceAdapter extends ArrayAdapter<Device> {
    private static final String PREFIX_RSSI = "RSSI:";
    private List<Device> mList;
    private LayoutInflater mInflater;
    private int mResId;

    public DeviceAdapter(Context context, int resId, List<Device> objects) {
        super(context, resId, objects);
        mResId = resId;
        mList = objects;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Device item = (Device) getItem(position);

        if (convertView == null) {
            convertView = mInflater.inflate(mResId, null);
        }
        TextView name = (TextView) convertView.findViewById(R.id.device_name);
        name.setText(item.getDisplayName());
        TextView address = (TextView) convertView.findViewById(R.id.device_address);
        address.setText(item.getDevice().getAddress());
        TextView rssi = (TextView) convertView.findViewById(R.id.device_rssi);
        rssi.setText(PREFIX_RSSI + Integer.toString(item.getRssi()));

        TextView state = (TextView) convertView.findViewById(R.id.device_state);
        state.setText(item.getState().name());

        return convertView;
    }

    public Device getDeviceByAddress(String address)
    {
        for (Device device : mList) {
            if (address.equals(device.getDevice().getAddress())) {
                return device;
            }
        }
        return null;
    }

    public void updateDeviceConnection(String address, RxBleConnection.RxBleConnectionState state)
    {
        Device device = getDeviceByAddress(address);
        if(device != null) {
            device.setState(state);
            notifyDataSetChanged();
        }
        return;
    }

    /**
     * add or update BluetoothDevice
     */
    public void update(BluetoothDevice newDevice, int rssi, RxBleConnection.RxBleConnectionState state) {
        if ((newDevice == null) || (newDevice.getAddress() == null)) {
            return;
        }

        Device device = getDeviceByAddress(newDevice.getAddress());
        if(device == null)
        {
            mList.add(new Device(newDevice, rssi));
        }
        else {
            device.setRssi(rssi);
            device.setState(state);
        }

        notifyDataSetChanged();
    }

}
