package com.autoskola.instruktori.services.model;

import com.google.gson.Gson;

/**
 * Created by haris on 1/25/15.
 */
public class Prijava{

    public String VoznjaId;
    public String DatumPrijave;
    public int KandidatId;
    public  String DatumVoznje;
    public  String VrijemeVoznje;
    public int Aktivno;
    public String Ime;
    public String Prezime;
    public  int InstruktorId;




    public String convertToJson() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }

    public Prijava convertFromJson(String json) {
        Gson gson = new Gson();
        Prijava obj = gson.fromJson(json, Prijava.class);
        return obj;
    }

    public String getVoznjaId() {
        return VoznjaId;
    }

    public void setVoznjaId(String voznjaId) {
        VoznjaId = voznjaId;
    }

    public String getDatumPrijave() {
        return DatumPrijave;
    }

    public void setDatumPrijave(String datumPrijave) {
        DatumPrijave = datumPrijave;
    }

    public int getKandidatId() {
        return KandidatId;
    }

    public void setKandidatId(int kandidatId) {
        KandidatId = kandidatId;
    }

    public String getDatumVoznje() {
        return DatumVoznje;
    }

    public void setDatumVoznje(String datumVoznje) {
        DatumVoznje = datumVoznje;
    }

    public String getVrijemeVoznje() {
        return VrijemeVoznje;
    }

    public void setVrijemeVoznje(String vrijemeVoznje) {
        VrijemeVoznje = vrijemeVoznje;
    }

    public int getAktivno() {
        return Aktivno;
    }

    public void setAktivno(int aktivno) {
        Aktivno = aktivno;
    }

    public String getIme() {
        return Ime;
    }

    public void setIme(String ime) {
        Ime = ime;
    }

    public String getPrezime() {
        return Prezime;
    }

    public void setPrezime(String prezime) {
        Prezime = prezime;
    }

    public int getInstruktorId() {
        return InstruktorId;
    }

    public void setInstruktorId(int instruktorId) {
        InstruktorId = instruktorId;
    }
}
