package com.XiaomiM365Locker.app;

import android.bluetooth.BluetoothDevice;

import com.polidea.rxandroidble2.RxBleConnection;

public class Device {
    private static final String UNKNOWN = "Unknown";
    /**
     * BluetoothDevice
     */
    private BluetoothDevice mDevice;
    /**
     * RSSI
     */
    private int mRssi;
    /**
     * Display Name
     */
    private String mDisplayName;
    private RxBleConnection.RxBleConnectionState state;

    public Device(BluetoothDevice device, int rssi) {
        if (device == null) {
            throw new IllegalArgumentException("BluetoothDevice is null");
        }
        mDevice = device;
        mDisplayName = device.getName();
        if ((mDisplayName == null) || (mDisplayName.length() == 0)) {
            mDisplayName = UNKNOWN;
        }
        mRssi = rssi;
        this.state = RxBleConnection.RxBleConnectionState.DISCONNECTED;
    }

    public BluetoothDevice getDevice() {
        return mDevice;
    }

    public int getRssi() {
        return mRssi;
    }

    public void setRssi(int rssi) {
        mRssi = rssi;
    }

    public void setState(RxBleConnection.RxBleConnectionState newstate)
    {
        this.state = newstate;
    }

    public RxBleConnection.RxBleConnectionState getState() {
        return this.state;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }
}
