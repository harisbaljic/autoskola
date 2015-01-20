package com.autoskola.instruktori.gps;

/**
 * Created by haris on 1/20/15.
 */
public class GpsTask {
    private static GpsTask ourInstance = new GpsTask();

    public static GpsTask getInstance() {
        return ourInstance;
    }

    private GpsTask() {
    }
}
