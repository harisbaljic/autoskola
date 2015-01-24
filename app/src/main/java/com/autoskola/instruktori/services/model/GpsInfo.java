package com.autoskola.instruktori.services.model;

import io.realm.RealmObject;

/**
 * Created by haris on 1/24/15.
 */
public class GpsInfo extends RealmObject{
    private String voznjaId;
    private String detaljiVoznjeId;
    private double longitude;
    private double latitude;

    public String getVoznjaId() {
        return voznjaId;
    }

    public void setVoznjaId(String voznjaId) {
        this.voznjaId = voznjaId;
    }

    public String getDetaljiVoznjeId() {
        return this.detaljiVoznjeId;
    }

    public void setDetaljiVoznjeId(String detaljiVoznjeId) {
        this.detaljiVoznjeId = detaljiVoznjeId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
