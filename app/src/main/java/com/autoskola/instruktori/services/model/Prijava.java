package com.autoskola.instruktori.services.model;

import com.google.gson.Gson;

/**
 * Created by haris on 1/25/15.
 */
public class Prijava{

    //    {
//        "VoznjaId": 106,
//            "PrijavaId": 2,
//            "VrijemeVoznje": "08:00h",
//            "DatumVoznje": "2015-02-21 00:00:00:000",
//            "Aktivno": 1,
//            "Ime": "Haris",
//            "Prezime": "Baljic",
//            "InstruktorId": 2
//    }
    public String VoznjaId;
    public String DatumPrijave;
    public int KandidatId;
    public  String DatumVoznje;
    public  String VrijemeVoznje;
    public String VrijemeVoznjeDo;
    public String Napomena;
    public int Aktivno;
    public String Ime;
    public String Prezime;
    public  int InstruktorId;
    public String PrijavaId;


    public String getVrijemeVoznjeDo() {
        return VrijemeVoznjeDo;
    }

    public void setVrijemeVoznjeDo(String vrijemeVoznjeDo) {
        VrijemeVoznjeDo = vrijemeVoznjeDo;
    }

    public String getNapomena() {
        return Napomena;
    }

    public void setNapomena(String napomena) {
        Napomena = napomena;
    }

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

    public String getPrijavaId() {
        return PrijavaId;
    }

    public void setPrijavaId(String prijavaId) {
        PrijavaId = prijavaId;
    }
}
