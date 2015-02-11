package com.autoskola.instruktori.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.autoskola.instruktori.R;
import com.autoskola.instruktori.adapters.ObavijestiAdapter;
import com.autoskola.instruktori.helpers.AppController;
import com.autoskola.instruktori.helpers.Helper;
import com.autoskola.instruktori.services.model.Obavijest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        //  progressDialog.show();

        super.onActivityCreated(savedInstanceState);
        listObavijesti = (ListView) getActivity().findViewById(
                R.id.activity_main_list_obavijesti);

        final AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.5f);
        alpha.setDuration(0); // Make animation instant
        alpha.setFillAfter(true); // Tell it to persist after the animation
        // ends

        adapter = new ObavijestiAdapter(getActivity(),obavijestList);
        listObavijesti.setAdapter(adapter);

        String url = Helper.getTop10Obavijesti;

        StringRequest obavijesti = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                obavijestList = Arrays.asList(new Gson().fromJson(s.toString(), Obavijest[].class));
                adapter = new ObavijestiAdapter(getActivity(),obavijestList);
                listObavijesti.setAdapter(adapter);

                listObavijesti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final Dialog obavijestDialog = new Dialog(
                                getActivity(),
                                android.R.style.Theme_Translucent);
                        obavijestDialog
                                .requestWindowFeature(Window.FEATURE_NO_TITLE);
                        obavijestDialog
                                .setContentView(R.layout.obavijest_dialog);
                        obavijestDialog
                                .setCanceledOnTouchOutside(true);
                        // clanakDialog.getWindow()
                        //   .getAttributes().windowAnimations = R.style.InitialLoadingDialogStyleAnimation;
                        obavijestDialog.findViewById(
                                R.id.obavijesti_dialog_img_close)
                                .setOnClickListener(
                                        new View.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    View v) {
                                                obavijestDialog.findViewById(
                                                        R.id.obavijesti_dialog_img_close).startAnimation(alpha);
                                                obavijestDialog
                                                        .dismiss();
                                            }
                                        });
                        TextView naslov = (TextView) obavijestDialog
                                .findViewById(R.id.obavijesti_dialog_text_naslov);
                        TextView tekst = (TextView) obavijestDialog
                                .findViewById(R.id.obavijesti_dialog_text_tekst);


                        if (obavijestList.get(position)
                                .getNaslov() != null)
                            naslov.setText(obavijestList.get(
                                    position).getNaslov());
                        else
                            naslov.setText("Naslov");
                        if (obavijestList.get(position)
                                .getSadrzaj() != null)
                            tekst.setText(obavijestList.get(
                                    position).getSadrzaj());
                        else
                            naslov.setText("Tekst");

                        obavijestDialog.show();

                    }
                });





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        AppController.getInstance().addToRequestQueue(obavijesti);



/*
        JsonArrayRequest obavijestiReq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.d(TAG, jsonArray.toString());

               for (int i=0; i<jsonArray.length(); i++)
                {
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        Obavijest o = new Obavijest();
                        o.setNaslov(obj.getString("Naslov"));
                        o.setSadrzaj(obj.getString("Sadrzaj"));


                        Datum d = new Datum(s,"1",1);

                        o.setDatumObjave(d);
                        o.setSlikaPutanja("http://projekt001.app.fit.ba" + obj.get("putanjaSlika"));
                        obavijestList.add(i,o);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                adapter.notifyDataSetChanged();

                listObavijesti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final Dialog obavijestDialog = new Dialog(
                                getActivity(),
                                android.R.style.Theme_Translucent);
                        obavijestDialog
                                .requestWindowFeature(Window.FEATURE_NO_TITLE);
                        obavijestDialog
                                .setContentView(R.layout.obavijest_dialog);
                        obavijestDialog
                                .setCanceledOnTouchOutside(true);
                        // clanakDialog.getWindow()
                        //   .getAttributes().windowAnimations = R.style.InitialLoadingDialogStyleAnimation;
                        obavijestDialog.findViewById(
                                R.id.obavijesti_dialog_img_close)
                                .setOnClickListener(
                                        new View.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    View v) {
                                                obavijestDialog.findViewById(
                                                        R.id.obavijesti_dialog_img_close).startAnimation(alpha);
                                                obavijestDialog
                                                        .dismiss();
                                            }
                                        });
                        TextView naslov = (TextView) obavijestDialog
                                .findViewById(R.id.obavijesti_dialog_text_naslov);
                        TextView tekst = (TextView) obavijestDialog
                                .findViewById(R.id.obavijesti_dialog_text_tekst);

                        if (obavijestList.get(position)
                                .getNaslov() != null)
                            naslov.setText(obavijestList.get(
                                    position).getNaslov());
                        else
                            naslov.setText("Naslov");
                        if (obavijestList.get(position)
                                .getSadrzaj() != null)
                            tekst.setText(obavijestList.get(
                                    position).getSadrzaj());
                        else
                            naslov.setText("Tekst");

                        obavijestDialog.show();

                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });






        AppController.getInstance().addToRequestQueue(obavijestiReq); */
    }
    Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d("Success Response: " + response.toString(),response);
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (error.networkResponse != null) {
                Log.d("Error Response code: " +  error.networkResponse.statusCode,error.getMessage());
            }
        }
    };

    }





