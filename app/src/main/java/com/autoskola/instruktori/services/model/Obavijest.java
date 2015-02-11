package com.autoskola.instruktori.services.model;


import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class Obavijest {

    private String Sadrzaj;
    private int ObavijestId, AdministratorId;
    @com.google.gson.annotations.SerializedName("Datum")
    private Datum datum;
    private boolean isImageSet, isTextSet;
    private String putanjaSlika;
    public Obavijest(String naslov, String sadrzaj, int obavijestId, int administratorId,
                     Datum Datum, String putanjaslika){

        Naslov = naslov;
        Sadrzaj = sadrzaj;
        ObavijestId = obavijestId;
        datum = Datum;
        putanjaSlika = putanjaslika;
        isImageSet = false;
    }

    public Obavijest() {

    }


    public String getNaslov() {
        return Naslov;
    }

    public void setNaslov(String naslov) {
        Naslov = naslov;
    }

    private String Naslov;



    public void setSlikaPutanja(String slikaPutanja) {
        slikaPutanja = slikaPutanja;
    }


    public String getSadrzaj() {
        return Sadrzaj;
    }

    public void setSadrzaj(String sadrzaj) {
        Sadrzaj = sadrzaj;
    }

    public int getObavijestId() {
        return ObavijestId;
    }

    public void setObavijestId(int obavijestId) {
        ObavijestId = obavijestId;
    }

    public int getAdministratorId() {
        return AdministratorId;
    }

    public void setAdministratorId(int administratorId) {
        AdministratorId = administratorId;
    }

    public Datum getDatumObjave() {
        return datum;
    }

    public void setDatumObjave(Datum Datum) {
        datum = Datum;
    }

    public boolean isImageSet() {
        return isImageSet;
    }

    public void setImageSet(boolean isImageSet) {
        this.isImageSet = isImageSet;
    }

    public boolean isTextSet() {
        return isTextSet;
    }

    public void setTextSet(boolean isTextSet) {
        this.isTextSet = isTextSet;
    }

    public String getPutanjaSlika() {
        return putanjaSlika;
    }

    public void setPutanjaSlika(String putanjaSlika) {
        this.putanjaSlika = putanjaSlika;
    }


}
