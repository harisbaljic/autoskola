package com.autoskola.instruktori.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.autoskola.instruktori.R;
import com.autoskola.instruktori.ui.adapters.PrijaveAdapter;
import com.autoskola.instruktori.helpers.ApplicationContext;
import com.autoskola.instruktori.services.PrijavaWebService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;


public class FragmentPrijave extends android.support.v4.app.Fragment {

    private ListView list;
    private List<com.autoskola.instruktori.services.model.Prijava> items = new ArrayList<com.autoskola.instruktori.services.model.Prijava>();
    private PrijaveAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_prijave, container, false);
        list = (ListView) view.findViewById(
                R.id.fragment_prijave_list);
        return view;

    }

    public void getInstruktorId(String korisnikId) {
        // Set endpoint
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://projekt001.app.fit.ba/autoskola")
                .build();

        // Generate service
        PrijavaWebService service = restAdapter.create(PrijavaWebService.class);

        // Callback
        Callback<com.autoskola.instruktori.services.model.Prijava> callback = new Callback<com.autoskola.instruktori.services.model.Prijava>() {
            @Override
            public void success(com.autoskola.instruktori.services.model.Prijava prijava, retrofit.client.Response response) {
               //
                if(prijava!=null)
                    Log.d("GET Instruktor - success:", String.valueOf(prijava.getInstruktorId()));

                // Get prijave
                getPrijave(String.valueOf(prijava.getInstruktorId()));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("GET Instruktor - fail:", error.toString());
            }
        };

        // GET request
        service.getInstruktorId(korisnikId, callback);
    }

    private void getPrijave(String korisnikId) {

        // Set endpoint
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://projekt001.app.fit.ba/autoskola")
                .build();

        // Generate service
        PrijavaWebService service = restAdapter.create(PrijavaWebService.class);

        // Callback
        Callback<List<com.autoskola.instruktori.services.model.Prijava>> callback = new Callback<List<com.autoskola.instruktori.services.model.Prijava>>() {
            @Override
            public void success(List<com.autoskola.instruktori.services.model.Prijava> prijavas, retrofit.client.Response response) {
                items = new ArrayList<>(prijavas);
                adapter = new PrijaveAdapter(getActivity(), items,FragmentPrijave.this);
                list.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        };

        // GET request
        service.getSvePrijave(korisnikId,callback);

    }

    public void getInstruktor (){
        getInstruktorId(ApplicationContext.getInstance().getLogiraniKorisnik().getKorisnikId() + "");
    }

    @Override
    public void onResume() {
        super.onResume();
       getInstruktor();
    }
}







