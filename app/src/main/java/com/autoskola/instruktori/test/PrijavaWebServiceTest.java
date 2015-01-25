package com.autoskola.instruktori.test;

import android.util.Log;

import com.autoskola.instruktori.services.PrijavaWebService;
import com.autoskola.instruktori.services.model.Prijava;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by haris on 1/25/15.
 */
public class PrijavaWebServiceTest {
    private static PrijavaWebServiceTest ourInstance = new PrijavaWebServiceTest();

    public static PrijavaWebServiceTest getInstance() {
        return ourInstance;
    }

    private PrijavaWebServiceTest() {
    }

    public void testGetAktivnePrijave(String instruktorId){
        // Set endpoint
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://projekt001.app.fit.ba/autoskola")
                .build();

        // Generate service
        PrijavaWebService service = restAdapter.create(PrijavaWebService.class);

        // Callback
        Callback<List<Prijava>> callback = new Callback<List<Prijava>>() {
            @Override
            public void success(List<Prijava> gpsInfo, Response response) {
                Log.d("GET Aktivne prijave - success:",gpsInfo.get(0).toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("GPS Aktivne prijave - fail:",error.toString());
            }
        };

        // GET request
        service.getAktivnePrijave(instruktorId,callback);
    }
}
