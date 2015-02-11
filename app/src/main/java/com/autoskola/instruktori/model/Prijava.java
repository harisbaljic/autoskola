package com.autoskola.instruktori.model;

/**
 * Created by The Boss on 12.1.2015.
 */
public class Prijava {

    int VoznjaId, InstruktorId, KandidatId, AutoSkolaId;
    String DatumPrijave, DatumVoznje;
    String VrijemeVoznje, VrijemeVoznjeDo, Napomena;
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



    public Prijava(int voznjaId, int instruktorId, int kandidatId, int autoskolaId, String datumPrijave,
                   String datumVoznje, String vrijemeVoznje, String imePrezime, Byte aktivno, String vrijemeVoznjeDo, String napomena) {
        VoznjaId = voznjaId;
        InstruktorId = instruktorId;
        KandidatId = kandidatId;
        AutoSkolaId = autoskolaId;
        this.DatumPrijave = datumPrijave;
        this.DatumVoznje = datumVoznje;
        VrijemeVoznje = vrijemeVoznje;
        VrijemeVoznjeDo = vrijemeVoznjeDo;
        Aktivno = aktivno;
        Napomena = napomena;
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

    public int getKandidatId() {
        return KandidatId;
    }

    public void setKandidatId(int kandidatId) {
        KandidatId = kandidatId;
    }

    public String getDatumPrijave() {
        return DatumPrijave;
    }

    public void setDatumPrijave(String datumPrijave) {
        DatumPrijave = datumPrijave;
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

    public Byte getAktivno() {
        return Aktivno;
    }

    public void setAktivno(Byte aktivno) {
        Aktivno = aktivno;
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
