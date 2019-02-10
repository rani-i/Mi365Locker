package com.XiaomiM365Locker.app;

import java.util.Arrays;

public class LockOn implements IRequest {
    private static int delay = 100;
    private final String requestBit = "70";
    private final RequestType requestType = RequestType.NOCOUNT;
    private long startTime;

    public LockOn() {
        this.startTime = System.currentTimeMillis() + delay;
    }

    @Override
    public int getDelay() {
        return delay;
    }

    @Override
    public String getRequestString() {
        return new NbMessage()
                .setDirection(NbCommands.MASTER_TO_M365)
                .setRW(NbCommands.WRITE)
                .setPosition(0x70)
                .setPayload(0x0001)
                .build();
    }

    @Override
    public String getRequestBit() {
        return requestBit;
    }

    @Override
    public String handleResponse(String[] request) {
        return Arrays.toString(request);
    }

    @Override
    public RequestType getRequestType() {
        return requestType;
    }
}
