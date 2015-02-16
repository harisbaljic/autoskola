package com.autoskola.instruktori.model;

/**
 * Created by The Boss on 12.1.2015.
 */
public class Prijava {

    int VoznjaId,  KandidatId;
    String  DatumVoznje;
    String VrijemeVoznje, VrijemeVoznjeDo, Napomena;
    String Ime;
    String Prezime;


    public Prijava(int voznjaId, int kandidatId, String datumVoznje,
                    String vrijemeVoznje,String vrijemeVoznjeDo,String napomena, String ime, String prezime ) {
        VoznjaId = voznjaId;
        KandidatId = kandidatId;
        this.DatumVoznje = datumVoznje;
        VrijemeVoznje = vrijemeVoznje;
        VrijemeVoznjeDo = vrijemeVoznjeDo;
        Ime = ime;
        Prezime = prezime;
        Napomena = napomena;
    }

    public String getPrezime() {
        return Prezime;
    }

    public void setPrezime(String prezime) {
        Prezime = prezime;
    }

    public String getIme() {
        return Ime;
    }

    public void setIme(String ime) {
        Ime = ime;
    }
    public int getVoznjaId() {
        return VoznjaId;
    }

    public void setVoznjaId(int voznjaId) {
        VoznjaId = voznjaId;
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
}
