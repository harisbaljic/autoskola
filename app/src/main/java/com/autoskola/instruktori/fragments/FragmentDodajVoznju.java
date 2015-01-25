package com.autoskola.instruktori.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.autoskola.instruktori.R;
import com.autoskola.instruktori.adapters.VoznjeAdapter;
import com.autoskola.instruktori.gps.GpsTask;
import com.autoskola.instruktori.helpers.AppController;
import com.autoskola.instruktori.services.PrijavaWebService;
import com.autoskola.instruktori.services.model.Prijava;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


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
        super.onActivityCreated(savedInstanceState);
        list = (ListView) getActivity().findViewById( R.id.activity_main_list_voznje);

        // Instruktor id
        String korisnikId = AppController.getInstance().getKorisnik().getKorisnikId();

        // Get all aktivne prijave
        getAktivnePrijave(korisnikId);

        // ListView item on click listener
        setListOnClickListener();
    }

    public void getAktivnePrijave(String instruktorId){
        // Set endpoint
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://projekt001.app.fit.ba/autoskola")
                .build();

        // Generate service
        PrijavaWebService service = restAdapter.create(PrijavaWebService.class);

        // Callback
        Callback<List<com.autoskola.instruktori.services.model.Prijava>> callback = new Callback<List<com.autoskola.instruktori.services.model.Prijava>>() {
            @Override
            public void success(List<com.autoskola.instruktori.services.model.Prijava> prijave, Response response) {
                Log.d("GET Aktivne prijave - success:","");
                items.addAll(prijave);
                adapter = new VoznjeAdapter(getActivity(), items);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("GET Aktivne prijave - fail:",error.toString());
            }
        };

        // GET request
        service.getAktivnePrijave(instruktorId,callback);
    }

    private void setListOnClickListener (){
        this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

                if (GpsTask.getInstance().getAktivnaPrijava(parent.getContext()) == null){
                    // Gps task not active
                    new SweetAlertDialog(parent.getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Da li si siguran?")
                            .setContentText("Da li zelis da krene pracenje voznje?")
                            .setConfirmText("Da!")
                            .setCancelText("Ne")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.cancel();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog
                                            .setTitleText("Odobreno!")
                                            .setContentText("Kretanje mozete pratiti na mapi.")
                                            .setConfirmText("Uredu")
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                                    // Start gps task
                                    Prijava prijava = items.get(position);
                                    GpsTask.getInstance().startGPSTask(prijava,parent.getContext());
                                    adapter.notifyDataSetChanged();

                                }
                            })
                            .show();

                }
                else
                {

                    // Gps task is active
                    // Check if selected objects in preference
                    Prijava selectedPrijava = items.get(position);
                    if(GpsTask.getInstance().getAktivnaPrijava(parent.getContext()).VoznjaId.matches(selectedPrijava.VoznjaId)){

                        new SweetAlertDialog(parent.getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Da li si siguran?")
                                .setContentText("Da li zelis prekinuti pracenje?")
                                .setConfirmText("Da!")
                                .showCancelButton(true)
                                .setCancelText("Ne")
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.cancel();
                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog
                                                .setTitleText("Novo pracenje je zapocelo!")
                                                .setContentText("Kretanje mozete pratiti na mapi.")
                                                .setConfirmText("Uredu")
                                                .setConfirmClickListener(null)
                                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                                        // Stop gps task
                                        GpsTask.getInstance().stopGpsTask(parent.getContext());
                                        adapter.notifyDataSetChanged();

                                    }
                                })
                                .show();

                    }
                    else
                    {
                        // Gps task is active
                        new SweetAlertDialog(parent.getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Da li si siguran?")
                                .setContentText("Trenutno postoji aktivna prijava, da li je zelis prekinuti i pokrenuti odabranu?")
                                .setConfirmText("Da!")
                                .setCancelText("Ne")
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.cancel();
                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog
                                                .setTitleText("Novo pracenje je zapocelo!")
                                                .setContentText("Kretanje mozete pratiti na mapi.")
                                                .setConfirmText("Uredu")
                                                .setConfirmClickListener(null)
                                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                                        // Start gps task
                                        Prijava prijava = items.get(position);
                                        GpsTask.getInstance().startGPSTask(prijava,parent.getContext());
                                        adapter.notifyDataSetChanged();

                                    }
                                })
                                .show();

                    }

                }

            }
        });
    }
}
