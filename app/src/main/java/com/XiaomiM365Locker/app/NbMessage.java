package com.XiaomiM365Locker.app;

import java.util.ArrayList;
import java.util.List;

public class NbMessage {
    private List<Integer> msg;

    private int direction;
    private int rw;
    private int position;
    private List<Integer> payload;
    private int checksum;

    public NbMessage() {
        direction = 0;
        rw = 0;
        position = 0;
        payload = null;
        checksum = 0;
    }

    public NbMessage setDirection(NbCommands drct) {
        direction = drct.getCommand();
        checksum += direction;

        return this;
    }

    public NbMessage setRW(NbCommands readOrWrite) { // read or write
        rw = readOrWrite.getCommand();
        checksum += rw;

        return this;
    }

    public NbMessage setPosition(int pos) {
        position = pos;
        checksum += position;

        return this;
    }

    public NbMessage setPayload(byte[] bytesToSend) {
        this.payload = new ArrayList<>();

        checksum += bytesToSend.length + 2;

        for (int b : bytesToSend) {
            this.payload.add(b);
            checksum += b;
        }

        return this;
    }

    public NbMessage setPayload(List<Integer> bytesToSend) {
        payload = bytesToSend;

        checksum += payload.size() + 2;

        for (int i : payload) {
            checksum += i;
        }
        return this;
    }

    public NbMessage setPayload(int singleByteToSend) {
        payload = new ArrayList<>();
        payload.add(singleByteToSend);

        checksum += 3;
        checksum += singleByteToSend;

        return this;
    }

    public String build() {
        setupHeaders();

        setupBody();

        calculateChecksum();

        return construct();
    }

    private void setupHeaders() {
        msg = new ArrayList<>(0);

        msg.add(0x55);
        msg.add(0xAA);
    }

    private void setupBody() {
        msg.add(payload.size() + 2);
        msg.add(direction);
        msg.add(rw);
        msg.add(position);

        for (Integer i : payload) {
            msg.add(i);
        }
    }

    private void calculateChecksum() {
        checksum ^= 0xffff;

        msg.add((checksum & 0xff));
        msg.add(checksum >> 8);
    }

    private String construct() {
        String result = "";
        for (Integer i : msg) {
            result += (i >= 0) && (i <= 15) ? "0" + Integer.toHexString(i) : Integer.toHexString(i);
        }
        return result;
    }
}