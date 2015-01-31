package com.autoskola.instruktori.model;

/**
 * Created by The Boss on 15.1.2015.
 */
public class Voznja {

    int VoznjaId, InstruktorId, PrijavaId;
    int Status;
    Datum PocetakVoznje;


    public Voznja(int voznjaId, int instruktorId, int prijavaId, Datum pocetakVoznje){
        VoznjaId = voznjaId;
        InstruktorId = instruktorId;
        PrijavaId = prijavaId;
        PocetakVoznje = pocetakVoznje;


    }

    public Voznja (){


    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getVoznjaId() {
        return VoznjaId;
    }

    public void setVoznjaId(int voznjaId) {
        VoznjaId = voznjaId;
    }

    public int getInstruktorId() {
        return InstruktorId;
    }

    public void setInstruktorId(int instruktorId) {
        InstruktorId = instruktorId;
    }

    public int getPrijavaId() {
        return PrijavaId;
    }

    public void setPrijavaId(int prijavaId) {
        PrijavaId = prijavaId;
    }


    public Datum getPocetakVoznje() {
        return PocetakVoznje;
    }

    public void setPocetakVoznje(Datum pocetakVoznje) {
        PocetakVoznje = pocetakVoznje;
    }
}
