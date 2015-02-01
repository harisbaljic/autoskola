package com.autoskola.instruktori.services.model;


import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class Obavijest {

    private String Sadrzaj;
    private int ObavijestId, AdministratorId;
    @SerializedName("DatumObjave")
    private com.autoskola.instruktori.model.Datum DatumObjave;
    private Bitmap SlikaObavijesti;
    private boolean isImageSet, isTextSet;

    public Obavijest(String naslov, String sadrzaj, int obavijestId, int administratorId,
                     com.autoskola.instruktori.model.Datum datumObjave, Bitmap slikaObavijesti){

        Naslov = naslov;
        Sadrzaj = sadrzaj;
        ObavijestId = obavijestId;
        DatumObjave = datumObjave;
        SlikaObavijesti = slikaObavijesti;
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

    public String getSlikaPutanja() {
        return SlikaPutanja;
    }

    public void setSlikaPutanja(String slikaPutanja) {
        SlikaPutanja = slikaPutanja;
    }

    private String SlikaPutanja;

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

    public com.autoskola.instruktori.model.Datum getDatumObjave() {
        return DatumObjave;
    }

    public void setDatumObjave(com.autoskola.instruktori.model.Datum datumObjave) {
        DatumObjave = datumObjave;
    }

    public Bitmap getSlikaObavijesti() {
        return SlikaObavijesti;
    }

    public void setSlikaObavijesti(Bitmap slikaObavijesti) {
        SlikaObavijesti = slikaObavijesti;
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



}
