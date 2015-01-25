package com.autoskola.instruktori.services;

import com.autoskola.instruktori.services.model.Prijava;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by haris on 1/25/15.
 */
public interface PrijavaWebService {
    // http://projekt001.app.fit.ba/autoskola/servis_AktivnePrijave.php?instruktorId=1
    @GET("/servis_AktivnePrijave.php")
    void getAktivnePrijave(@Query("instruktorId") String instruktorId, Callback<List<Prijava>> cb);
}
