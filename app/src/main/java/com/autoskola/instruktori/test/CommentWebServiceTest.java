package com.autoskola.instruktori.test;

import com.autoskola.instruktori.services.GpsWebService;
import com.autoskola.instruktori.services.model.Komentar;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by haris on 2/14/15.
 */
public class CommentWebServiceTest {
    private static CommentWebServiceTest ourInstance = new CommentWebServiceTest();

    public static CommentWebServiceTest getInstance() {
        return ourInstance;
    }

    private CommentWebServiceTest() {
    }


    public void postCommentData(List<Komentar> listKomentara){

        // Set endpoint
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://projekt001.app.fit.ba/autoskola")
                .build();

        // Generate service
        GpsWebService service = restAdapter.create(GpsWebService.class);

        // Callback
        Callback<List<Komentar>> callback = new Callback<List<Komentar>>() {
            @Override
            public void success(List<Komentar> aBoolean, Response response) {
                System.out.println("POST Comments - success");
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("POST Comments - fail:"+error);
            }
        };

        // POST request
        service.postGpsKomentar(listKomentara, callback);
    }
}
