package com.autoskola.instruktori.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.autoskola.instruktori.R;
import com.autoskola.instruktori.adapters.VoznjeAdapter;
import com.autoskola.instruktori.helpers.AppController;
import com.autoskola.instruktori.helpers.Helper;
import com.autoskola.instruktori.model.Prijava;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FragmentDodajVoznju extends Fragment {
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
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        list = (ListView) getActivity().findViewById(
                R.id.activity_main_list_voznje);


       /* final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show(); */

        final String korisnikId = AppController.getInstance().getKorisnik().getKorisnikId();


        final StringRequest getAktivnePrijaveRequest = new StringRequest(
                Request.Method.POST,
                Helper.aktivnePrijave,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String arg0) {
                        Log.d("temp", arg0.toString());
                        items = Arrays.asList(new Gson().fromJson(
                                arg0.toString(), Prijava[].class));
                        Log.d("temp", new Gson().toJson(items));
                        adapter = new VoznjeAdapter(getActivity(), items);
                        list.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {

                Log.e("onErrorResponse", arg0.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("kandidatId", korisnikId + "");

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(getAktivnePrijaveRequest);

        // ListView item on click listener
        setListOnClickListener();
    }

    private void setListOnClickListener (){
        this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Position:"+position);
            }
        });
    }
}
