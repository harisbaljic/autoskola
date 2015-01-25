package com.autoskola.instruktori.services.model;

import com.google.gson.Gson;

/**
 * Created by haris on 1/25/15.
 */
public class Prijava{

    public String VoznjaId;
    public Datum DatumPrijave;
    public int KandidatId;
    public  Datum DatumVoznje;
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
}
