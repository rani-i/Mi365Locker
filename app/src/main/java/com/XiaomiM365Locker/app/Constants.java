package com.XiaomiM365Locker.app;

public class Constants {

    public static final String CHAR_WRITE = "6e400002-b5a3-f393-e0a9-e50e24dcca9e"; //WRITE
    public static final String CHAR_READ = "6e400003-b5a3-f393-e0a9-e50e24dcca9e"; //READ
    public static final String TAG = "MyAppTag";


    public static int BASE_DELAY = 300;
    public static int QUEUE_DELAY = 400;
    private static int VOLTAGE_DELAY = 400;
    private static int AMPERE_DELAY = 100;
    private static int BATTERYLIFE_DELAY = 2000;
    private static int SPEED_DELAY = 100;
    private static int DISTANCE_DELAY = 400;

    public static int getVoltageDelay() {
        return VOLTAGE_DELAY + BASE_DELAY;
    }

    public static int getAmpereDelay() {
        return AMPERE_DELAY + BASE_DELAY;
    }

    public static int getBatterylifeDelay() {
        return BATTERYLIFE_DELAY + BASE_DELAY;
    }

    public static int getSpeedDelay() {
        return SPEED_DELAY + BASE_DELAY;
    }

    public static int getDistanceDelay() {
        return DISTANCE_DELAY + BASE_DELAY;
    }

    public static int getLogDelay() {
        return 500;
    }
}