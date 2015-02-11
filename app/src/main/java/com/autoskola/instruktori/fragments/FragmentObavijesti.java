package com.autoskola.instruktori.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.autoskola.instruktori.R;
import com.autoskola.instruktori.adapters.ObavijestiAdapter;
import com.autoskola.instruktori.services.PrijavaWebService;
import com.autoskola.instruktori.services.model.Obavijest;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class FragmentObavijesti extends android.support.v4.app.Fragment {
    private String TAG = "MainActivity_LOG";
    private ListView listObavijesti;
    ObavijestiAdapter adapter;
    List<Obavijest> obavijestList = new ArrayList<Obavijest>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_obavijesti, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listObavijesti = (ListView) getActivity().findViewById(
                R.id.activity_main_list_obavijesti);
        getObavijesti();
    }

    public void getObavijesti (){
        // Set endpoint
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://projekt001.app.fit.ba/autoskola")
                .build();

        // Generate service
        PrijavaWebService service = restAdapter.create(PrijavaWebService.class);

        // Callback
        Callback<List<Obavijest>> callback = new Callback<List<Obavijest>>() {
            @Override
            public void success(List<Obavijest> obavijestiList, Response response) {
                Log.d("GET comments - success:", "");
                obavijestList = new ArrayList<>(obavijestiList);
                adapter = new ObavijestiAdapter(getActivity(),obavijestiList);
                listObavijesti.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("GET comments - fail:",error.toString());
            }
        };

        // GET request
        service.getObavijesti(callback);
    }
}
