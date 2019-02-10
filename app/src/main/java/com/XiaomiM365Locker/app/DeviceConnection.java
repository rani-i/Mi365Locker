package com.XiaomiM365Locker.app;

import android.util.Log;

import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleDevice;

import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.reactivex.disposables.Disposable;
import android.app.Activity;

public class DeviceConnection {
    private RxBleDevice device;
    private RxBleConnection connection;
    private Disposable connection_disposable;
    private boolean written_success = false;
    private ConcurrentLinkedQueue<IRequest> command_to_execute;
    private DeviceAdapter deviceAdapter;
    private Activity activity;

    DeviceConnection(RxBleDevice device, DeviceAdapter deviceAdapter, Activity activity) {
        this.device = device;
        this.command_to_execute = new ConcurrentLinkedQueue<>();

        this.activity = activity;
        this.deviceAdapter = deviceAdapter;

        this.setupConnection();
    }

    public void addCommand(IRequest request) {
        this.command_to_execute.add(request);
    }

    public IRequest get_first_command()
    {
        return this.command_to_execute.peek();
    }

    public void runNextCommand()
    {
        if(this.device.getConnectionState() == RxBleConnection.RxBleConnectionState.CONNECTED && this.connection != null) {

            IRequest command = this.command_to_execute.remove();
            if(command != null)
            {
                this.sendCommand(command);
            }
        }
    }


    public RxBleConnection.RxBleConnectionState getState()
    {
        return this.device.getConnectionState();
    }

    public void setupConnection()
    {
        this.connection_disposable = this.device.establishConnection(false).doFinally(this::dispose)
            .subscribe(this::onConnectReceived, this::onConnectionFailure);

        this.device.observeConnectionStateChanges().subscribe(this::onObserveState,
                this::onObserveStateFailure);
    }

    private void onObserveState(RxBleConnection.RxBleConnectionState newState)
    {
        this.updateDeviceStatus();
    }
    private void onObserveStateFailure(Throwable e){
        Log.e(Constants.TAG, "ObserveFailed", e);
        this.connection = null;
    }
    private void onConnectReceived(RxBleConnection connection){
        this.connection = connection;
    }

    private void updateDeviceStatus()
    {
        String address = this.device.getMacAddress();
        RxBleConnection.RxBleConnectionState state = this.device.getConnectionState();

        Log.d(Constants.TAG, address+" : "+state);

        activity.runOnUiThread(() -> {
            deviceAdapter.updateDeviceConnection(address, state);
        });
    }

    private void onConnectionFailure(Throwable e)
    {
        Log.e(Constants.TAG, "Connection failure", e);
        this.connection = null;
    }

    public void dispose() {

        if(this.connection_disposable != null) {
            this.connection_disposable.dispose();
            this.connection_disposable = null;
        }
    }
    private void onWriteSuccess()
    {
        this.written_success = true;
    }

    private  void onWriteFailure(Throwable throwable)
    {
        Log.e(Constants.TAG, "Error writing", throwable);
    }
    private boolean sendCommand(IRequest request) {
        if(device.getConnectionState() != RxBleConnection.RxBleConnectionState.CONNECTED) {
            return false;
        }

        String command = request.getRequestString();

        this.written_success = false;
        this.connection.writeCharacteristic(UUID.fromString(Constants.CHAR_WRITE), HexString.hexToBytes(command)).subscribe((byte[] bytes)->this.onWriteSuccess(), throwable -> this.onWriteFailure(throwable));

        if(this.written_success)
            return true;
        return false;
    }

}
