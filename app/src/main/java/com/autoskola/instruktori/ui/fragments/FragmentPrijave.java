package com.autoskola.instruktori.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.autoskola.instruktori.R;
import com.autoskola.instruktori.helpers.ApplicationContext;
import com.autoskola.instruktori.services.PrijavaWebService;
import com.autoskola.instruktori.ui.adapters.PrijaveAdapter;

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

    private void getPrijave(String instruktorId) {

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
        service.getSvePrijave(instruktorId,callback);

    }


    public void getPrijave(){
        getPrijave(ApplicationContext.getInstance().getLogiraniKorisnik().InstruktorId);
    }

    @Override
    public void onResume() {
        super.onResume();
         getPrijave();
    }
}







