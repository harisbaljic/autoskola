package com.autoskola.instruktori.model;

/**
 * Created by The Boss on 12.1.2015.
 */
public class Prijava {

    int VoznjaId, InstruktorId, KandidatId;
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

    public String getIme() {
        return Ime;
    }

    public void setIme(String ime) {
        Ime = ime;
    }



    public Prijava(int voznjaId, String datumPrijave,int kandidatId, String datumVoznje,
                    String vrijemeVoznje,String vrijemeVoznjeDo,String napomena, Byte aktivno, String ime, String prezime,  int instruktorId  ) {
        VoznjaId = voznjaId;
        InstruktorId = instruktorId;
        KandidatId = kandidatId;
        this.DatumPrijave = datumPrijave;
        this.DatumVoznje = datumVoznje;
        VrijemeVoznje = vrijemeVoznje;
        VrijemeVoznjeDo = vrijemeVoznjeDo;
        Ime = ime;
        Prezime = prezime;
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
