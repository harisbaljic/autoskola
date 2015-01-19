package com.autoskola.instruktori.model;

/**
 * Created by The Boss on 12.1.2015.
 */
public class Prijava {

    int PrijavaId, InstruktorId, KandidatId, AutoSkolaId;
    Datum DatumPrijave, DatumVoznje;
    String VrijemeVoznje;
    String Ime;
    String Prezime;
    Byte Aktivno;

    public String getPrezime() {
        return Prezime;
    }

    public void setPrezime(String prezime) {
        Prezime = prezime;
    }

    public int getAutoSkolaId() {
        return AutoSkolaId;
    }

    public void setAutoSkolaId(int autoSkolaId) {
        AutoSkolaId = autoSkolaId;
    }

    public String getIme() {
        return Ime;
    }

    public void setIme(String ime) {
        Ime = ime;
    }



    public Prijava(int prijavaId, int instruktorId, int kandidatId, int autoskolaId, Datum datumPrijave, Datum datumVoznje, String vrijemeVoznje, String imePrezime, Byte aktivno) {
        PrijavaId = prijavaId;
        InstruktorId = instruktorId;
        KandidatId = kandidatId;
        AutoSkolaId = autoskolaId;
        DatumPrijave = datumPrijave;
        DatumVoznje = datumVoznje;
        VrijemeVoznje = vrijemeVoznje;
        Aktivno = aktivno;
    }

    public  Prijava(){    }



    public int getPrijavaId() {
        return PrijavaId;
    }

    public void setPrijavaId(int prijavaId) {
        PrijavaId = prijavaId;
    }

    public int getInstruktorId() {
        return InstruktorId;
    }

    public void setInstruktorId(int instruktorId) {
        InstruktorId = instruktorId;
    }

    public int getKandidatId() {
        return KandidatId;
    }

    public void setKandidatId(int kandidatId) {
        KandidatId = kandidatId;
    }

    public Datum getDatumPrijave() {
        return DatumPrijave;
    }

    public void setDatumPrijave(Datum datumPrijave) {
        DatumPrijave = datumPrijave;
    }

    public Datum getDatumVoznje() {
        return DatumVoznje;
    }

    public void setDatumVoznje(Datum datumVoznje) {
        DatumVoznje = datumVoznje;
    }

    public String getVrijemeVoznje() {
        return VrijemeVoznje;
    }

    public void setVrijemeVoznje(String vrijemeVoznje) {
        VrijemeVoznje = vrijemeVoznje;
    }

    public Byte getAktivno() {
        return Aktivno;
    }

    public void setAktivno(Byte aktivno) {
        Aktivno = aktivno;
    }
}
