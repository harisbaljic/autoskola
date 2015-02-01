package com.autoskola.instruktori.services.model;

import io.realm.RealmObject;

/**
 * Created by haris on 1/31/15.
 */
public class Komentar extends RealmObject{
    private String ltd;
    private String lng;
    private String voznjaId;
    private String datum;
    private String opis;
    private int isSynced; // 0 - out of sync
                          // 1 - in progress
                          // 2 - synced


    public String getLtd() {
        return ltd;
    }

    public void setLtd(String ltd) {
        this.ltd = ltd;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getVoznjaId() {
        return voznjaId;
    }

    public void setVoznjaId(String voznjaId) {
        this.voznjaId = voznjaId;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(int isSynced) {
        this.isSynced = isSynced;
    }
}
