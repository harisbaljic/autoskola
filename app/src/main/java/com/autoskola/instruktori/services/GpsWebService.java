package com.autoskola.instruktori.services;

import com.autoskola.instruktori.services.model.GpsInfo;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by haris on 1/24/15.
 */
public interface GpsWebService {

    // http://projekt001.app.fit.ba/autoskola/servis_getGpsPodaci.php?voznjaID=1
    @GET("/servis_getGpsPodaci.php")
    void getGpsInfo(@Query("voznjaID") String voznjaId, Callback<List<GpsInfo>> cb);

    // http://projekt001.app.fit.ba/autoskola/postArrayTest.php
    @POST("/postArrayTest.php")
    void createGpsInfo(@Body List<GpsInfo> info,Callback<List<GpsInfo>> cb);

}
