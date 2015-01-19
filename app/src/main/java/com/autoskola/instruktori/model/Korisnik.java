package com.autoskola.instruktori.model;

/**
 * Created by The Boss on 4.1.2015.
 */
public class Korisnik {

    public String Ime,Prezime,KorisnickoIme,Email,Adresa,Grad,LozinkaHash;
    public String KorisnikId;

    public Korisnik(String ime, String prezime, String korisnickoIme, String email, String adresa, String grad, String lozinkaHash, String korisnikId){

        Ime = ime;
        KorisnikId = korisnikId;
        KorisnickoIme = korisnickoIme;
        Prezime = prezime;
        Email = email;
        Adresa = adresa;
        Grad  = grad;
        LozinkaHash = lozinkaHash;
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

    public String getKorisnickoIme() {
        return KorisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        KorisnickoIme = korisnickoIme;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAdresa() {
        return Adresa;
    }

    public void setAdresa(String adresa) {
        Adresa = adresa;
    }

    public String getGrad() {
        return Grad;
    }

    public void setGrad(String grad) {
        Grad = grad;
    }

    public String getLozinkaHash() {
        return LozinkaHash;
    }

    public void setLozinkaHash(String lozinkaHash) {
        LozinkaHash = lozinkaHash;
    }

    public String getKorisnikId() {
        return KorisnikId;
    }

    public void setKorisnikId(String korisnikId) {
        KorisnikId = korisnikId;
    }
}
