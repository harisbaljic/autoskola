package com.autoskola.instruktori.services;

import com.autoskola.instruktori.services.model.Obavijest;
import com.autoskola.instruktori.services.model.Prijava;
import com.autoskola.instruktori.services.model.VoznjaSimple;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by haris on 1/25/15.
 */
public interface PrijavaWebService {
    // http://projekt001.app.fit.ba/autoskola/servis_AktivnePrijave.php?instruktorId=1
    @GET("/servis_AktivnePrijave.php")
    void getAktivnePrijave(@Query("instruktorId") String instruktorId, Callback<List<Prijava>> cb);

    //http://projekt001.app.fit.ba/autoskola/servis_InstruktorZavrseneVoznje.php?instruktorId=1
    @GET("/servis_InstruktorZavrseneVoznje.php")
    void getZavrseneVoznje(@Query("instruktorId") String instruktorId, Callback<List<Prijava>> cb);

    @POST("/servis_VoznjeUpdate.php")
    void updateVoznje (@Body VoznjaSimple info,Callback<VoznjaSimple> cb);

    //http://projekt001.app.fit.ba/autoskola/servis_ObavijestiTop10.php
    @GET("/servis_ObavijestiTop10.php")
    void getObavijesti(Callback<List<Obavijest>> cb);

}
