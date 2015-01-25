package com.autoskola.instruktori.gps;

/**
 * Created by haris on 1/20/15.
 */
public enum GpsResponseTypes {


    /**
     * Internet connection states
     */
    WI_FI_CONNECTION,
    MOBILE_CONNECTION,
    NO_INTERNET_CONNECTION,


    /**
     * GPS offline data synchronization
     */
    GPS_SYNC_SUCCESS,
    GPS_SYNC_FAIL,


    // GPS LOCATION CHANGE
    GPS_LOCATION_CHANGED
}
