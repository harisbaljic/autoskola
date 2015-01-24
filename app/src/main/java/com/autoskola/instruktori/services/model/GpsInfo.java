package com.autoskola.instruktori.services.model;

/**
 * Created by haris on 1/24/15.
 */
public class GpsInfo {
    public String voznjaId;
    public String DetaljiVoznjeId;
    public double longitude;
    public double latitude;

    @Override
    public String toString() {
        return "GpsInfo{" +
               // "DetaljiVoznjeId='" + DetaljiVoznjeId + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
