package com.autoskola.instruktori.services.test;

import android.util.Log;

import com.autoskola.instruktori.services.GpsWebService;
import com.autoskola.instruktori.services.model.GpsInfo;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by haris on 1/24/15.
 */
public class GpsWebServiceTest {
    private static GpsWebServiceTest ourInstance = new GpsWebServiceTest();

    public static GpsWebServiceTest getInstance() {
        return ourInstance;
    }

    private GpsWebServiceTest() {
    }

    public void testPostData (List<GpsInfo>gpsData){

        // Set endpoint
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://projekt001.app.fit.ba/autoskola")
                .build();

        // Generate service
        GpsWebService service = restAdapter.create(GpsWebService.class);

        // Callback
        Callback<List<GpsInfo>> callback = new Callback<List<GpsInfo>>() {
            @Override
            public void success(List<GpsInfo> aBoolean, Response response) {
                System.out.println("POST GpsInfo - success");
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("POST GpsInfo - fail:"+error);
            }
        };

        // POST request
        service.postGpsInfo(gpsData, callback);
    }

    private void testGetGpsData(String voznjaId){
        // Set endpoint
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://projekt001.app.fit.ba/autoskola")
                .build();

        // Generate service
        GpsWebService service = restAdapter.create(GpsWebService.class);

        // Callback
        Callback<List<GpsInfo>>callback = new Callback<List<GpsInfo>>() {
            @Override
            public void success(List<GpsInfo> gpsInfo, Response response) {
                Log.d("GET GpsInfo - success:", gpsInfo.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("GPS GpsInfo - fail:",error.toString());
            }
        };

        // GET request
        service.getGpsInfo(voznjaId,callback);
    }
}
