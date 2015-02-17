package com.autoskola.instruktori.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.autoskola.instruktori.R;
import com.autoskola.instruktori.adapters.VoznjeAdapter;
import com.autoskola.instruktori.helpers.AppController;
import com.autoskola.instruktori.services.PrijavaWebService;
import com.autoskola.instruktori.services.model.Prijava;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by haris on 1/31/15.
 */
public class FragmentZavrseneVoznje extends Fragment {

    private ListView list;
    private List<Prijava> items = new ArrayList<Prijava>();
    private VoznjeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dodaj_voznju, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list = (ListView) getActivity().findViewById(R.id.activity_main_list_voznje);

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
                if (prijava != null) {
                    // Get zavrsene voznje
                    getZavrseneVoznje(String.valueOf(prijava.getInstruktorId()));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("GPS Instruktor - fail:", error.toString());
            }
        };

        // GET request
        service.getInstruktorId(korisnikId, callback);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Instruktor id
        getInstruktorId(AppController.getInstance().getLogiraniKorisnik().getKorisnikId() + "");

        // Set on click listener
        setListener();
    }

    private void setListener() {

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prijava selected = items.get(position);

                // DialogFragment.show() will take care of adding the fragment
                // in a transaction.  We also want to remove any currently showing
                // dialog, so make our own transaction and take care of that here.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                // Create and show the dialog.
                FragmentZavrseneVoznjeDetalji newFragment = FragmentZavrseneVoznjeDetalji.newInstance(selected.VoznjaId);
                newFragment.show(ft, "dialog");
            }
        });
    }


    public void getZavrseneVoznje(String instruktorId) {
        // Set endpoint
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://projekt001.app.fit.ba/autoskola")
                .build();

        // Generate service
        PrijavaWebService service = restAdapter.create(PrijavaWebService.class);

        // Callback
        Callback<List<Prijava>> callback = new Callback<List<com.autoskola.instruktori.services.model.Prijava>>() {
            @Override
            public void success(List<com.autoskola.instruktori.services.model.Prijava> prijave, Response response) {
                Log.d("GET Aktivne prijave - success:", "");
                items = new ArrayList<>(prijave);
                adapter = new VoznjeAdapter(getActivity(), items);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("GET Aktivne prijave - fail:", error.toString());
            }
        };

        // GET request
        service.getZavrseneVoznje(instruktorId, callback);
    }
}
