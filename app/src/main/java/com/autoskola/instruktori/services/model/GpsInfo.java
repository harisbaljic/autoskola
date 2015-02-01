package com.autoskola.instruktori.services.model;

import io.realm.RealmObject;

/**
 * Created by haris on 1/24/15.
 */
public class GpsInfo extends RealmObject{

    private String voznjaId;
    private String detaljiVoznjeId;
    private String longitude;
    private String latitude;
    private int isSynced;


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

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(int isSynced) {
        this.isSynced = isSynced;
    }
}
