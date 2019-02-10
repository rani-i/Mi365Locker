package com.XiaomiM365Locker.app;

public enum NbCommands {
    MASTER_TO_M365(0x20),
    MASTER_TO_BATTERY(0x22),
    READ(0x01),
    WRITE(0x03);

    private final int command;

    NbCommands(int command) {
        this.command = command;
    }

    public int getCommand() {
        return this.command;
    }

}
