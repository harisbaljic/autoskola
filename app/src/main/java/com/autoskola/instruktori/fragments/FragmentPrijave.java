package com.autoskola.instruktori.fragments;

import android.app.Dialog;
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
import com.autoskola.instruktori.adapters.PrijaveAdapter;
import com.autoskola.instruktori.helpers.AppController;
import com.autoskola.instruktori.helpers.Helper;
import com.autoskola.instruktori.model.Prijava;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FragmentPrijave extends android.support.v4.app.Fragment {

    private ListView list;
    private List<Prijava> items = new ArrayList<Prijava>();
    private PrijaveAdapter adapter;
    private int korId = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prijave, container, false);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        list = (ListView) getActivity().findViewById(
                R.id.fragment_prijave_list);

        StringRequest getInstruktorId = new StringRequest(Request.Method.POST, Helper.SelectKorisnikID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);

                            korId = jsonObject
                                    .getJSONObject(
                                            "instruktorId")
                                    .getInt("InstruktorId");
                        } catch (JSONException e) {
                            // TODO Auto-generated catch
                            // block
                            Log.e("JSON Parser", "Error parsing data " + e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Error u getInstruktor", "Error u getInst");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("korisnikId", AppController.getInstance().getKorisnik().getKorisnikId() + "");
                return params;
            }
        };

        AppController.getInstance().getRequestQueue().add(getInstruktorId);


        if(korId !=0) {
            final StringRequest getPrijaveRequest = new StringRequest(
                    Request.Method.GET,
                    Helper.prijavaSelect,
                    new Response.Listener<String>() {

//
                        @Override
                        public void onResponse(String arg0) {
                            Log.d("temp", arg0.toString());
                            items = Arrays.asList(new Gson().fromJson(
                                    arg0.toString(), Prijava[].class));
                            Log.d("temp", new Gson().toJson(items));
                            adapter = new PrijaveAdapter(getActivity(), items);
                            list.setAdapter(adapter);

                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {



                                    final Dialog dialog = new Dialog(getActivity());
                                    dialog.setContentView(R.layout.list_item_prijave_inflater);

                                    dialog.findViewById(R.id.list_item_prijava_inflater_btn_odobri)
                                            .setOnClickListener(new View.OnClickListener() {

                                                @Override
                                                public void onClick(View v) {


                                                }
                                            });

                                }
                            });

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError arg0) {

                    Log.d("test", "Ušao u error");
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("instruktorId", korId + "");
                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(getPrijaveRequest);

        }
        }

    }






