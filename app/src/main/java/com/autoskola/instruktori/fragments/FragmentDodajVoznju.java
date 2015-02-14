package com.autoskola.instruktori.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.autoskola.instruktori.R;
import com.autoskola.instruktori.adapters.SyncStatusAdapter;
import com.autoskola.instruktori.adapters.VoznjeAdapter;
import com.autoskola.instruktori.gps.GpsTask;
import com.autoskola.instruktori.helpers.AppController;
import com.autoskola.instruktori.helpers.NetworkConnectivity;
import com.autoskola.instruktori.services.PrijavaWebService;
import com.autoskola.instruktori.services.model.Prijava;
import com.autoskola.instruktori.services.model.Voznja;
import com.autoskola.instruktori.services.model.VoznjaSimple;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class FragmentDodajVoznju extends android.support.v4.app.Fragment {

    private ListView list;
    private List<Prijava> items = new ArrayList<Prijava>();
    private List<Voznja> itemsOffline = new ArrayList<Voznja>();
    private TextView txtErrorLog;

    private VoznjeAdapter adapter;
    private SyncStatusAdapter mOfflineObjectAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view =   inflater.inflate(R.layout.fragment_dodaj_voznju, container, false);
       list = (ListView) view.findViewById( R.id.activity_main_list_voznje);
       txtErrorLog = (TextView)view.findViewById(R.id.txtErrorLog);
       return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();


        if (NetworkConnectivity.isConnected(this.getActivity())){
            // Instruktor id
            String korisnikId = AppController.getInstance().getKorisnik().getKorisnikId();

            GpsTask.getInstance().showMessage("Ima interneta, pokusavam getati voznje");
            // Get all aktivne prijave
            getAktivnePrijave(korisnikId);

            // ListView item on click listener
            setListOnClickListener();

        }
        else
        {
            // Offline data
            getAllOfflineVoznje();
            setListOnClickListenerForOffline();
            GpsTask.getInstance().showMessage("Nemas internet");
        }
    }

    private  void getAllOfflineVoznje () {
        new Thread(new Runnable() {
            public void run() {

                Realm realm = Realm.getInstance(getActivity());
                final RealmResults<Voznja> voznjaList = realm.where(Voznja.class)
                        .equalTo("status",0)
                        .findAll();
                itemsOffline = new ArrayList<Voznja>(voznjaList);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       mOfflineObjectAdapter = new SyncStatusAdapter(getActivity(),voznjaList);
                       list.setAdapter(mOfflineObjectAdapter);
                    }
                });

            }
        }).start();

    }


    private void refreshAktivnePrijave (){

        // Instruktor id
        String korisnikId = AppController.getInstance().getKorisnik().getKorisnikId();

        // Get all aktivne prijave
        getAktivnePrijave(korisnikId);
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
                txtErrorLog.setVisibility(View.INVISIBLE);
                items = new ArrayList<>(prijave);
                adapter = new VoznjeAdapter(getActivity(), items);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                GpsTask.getInstance().showMessage("Vraceno je :"+prijave.size() +" voznji");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("GET Aktivne prijave - fail:",error.toString());
                txtErrorLog.setVisibility(View.VISIBLE);
                txtErrorLog.setText(error.getLocalizedMessage());
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
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
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
                    final Prijava selectedPrijava = items.get(position);
                    if(GpsTask.getInstance().getAktivnaPrijava(parent.getContext()).VoznjaId.matches(selectedPrijava.VoznjaId)){

                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
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
                                                .setTitleText("Info!")
                                                .setContentText("Voznja je zavrsena!.")
                                                .setConfirmText("Uredu")
                                                .setConfirmClickListener(null)
                                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                                        // Stop gps task
                                        GpsTask.getInstance().stopGpsTask(parent.getContext());
                                        adapter.notifyDataSetChanged();


                                        // Try sync data with server
                                        tryPostData(selectedPrijava);

                                    }
                                })
                                .show();

                    }
                    else
                    {
                        // Gps task is active
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Da li si siguran?")
                                .setContentText("Trenutno postoji aktivna voznja, da li je zelis prekinuti i pokrenuti odabranu?")
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


    private void setListOnClickListenerForOffline (){
        this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {


                Voznja voznja = itemsOffline.get(position);
               final Prijava selectedPrijava = new Prijava();
                selectedPrijava.VoznjaId = voznja.getVoznjaId();
                selectedPrijava.Ime=voznja.getIme();
                selectedPrijava.Prezime = voznja.getPrezime();

                // Start gps task
                if (GpsTask.getInstance().getAktivnaPrijava(parent.getContext()) == null){
                    // Gps task not active
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
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



                                    GpsTask.getInstance().startGPSTask(selectedPrijava,parent.getContext());
                                    mOfflineObjectAdapter.notifyDataSetChanged();

                                }
                            })
                            .show();

                }
                else
                {

                    // Gps task is active
                    // Check if selected objects in preference
                    if(GpsTask.getInstance().getAktivnaPrijava(parent.getContext()).VoznjaId.matches(selectedPrijava.VoznjaId)){

                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
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
                                                .setTitleText("Info!")
                                                .setContentText("Voznja je zavrsena!.")
                                                .setConfirmText("Uredu")
                                                .setConfirmClickListener(null)
                                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                                        // Stop gps task
                                        GpsTask.getInstance().stopGpsTask(parent.getContext());

                                        // Try sync data with server
                                        tryPostData(selectedPrijava);

                                        getAllOfflineVoznje();

                                    }
                                })
                                .show();

                    }
                    else
                    {
                        // Gps task is active
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Da li si siguran?")
                                .setContentText("Trenutno postoji aktivna voznja, da li je zelis prekinuti i pokrenuti odabranu?")
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
                                        getAllOfflineVoznje();

                                    }
                                })
                                .show();

                    }

                }

            }
        });
    }



    private void  tryPostData (Prijava prijava){

        if (NetworkConnectivity.isConnected(getActivity())) {
            // Update status
            VoznjaSimple voznja = new VoznjaSimple();
            voznja.setVoznjaId(prijava.VoznjaId);
            voznja.setStatus(1);
            updateVoznje(voznja);

            // Sync comments
            GpsTask.getInstance().syncComments(getActivity());
            GpsTask.getInstance().syncGpsInfo(getActivity());
        }
        else
        {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Uhhh! Nemas interneta!")
                    .setContentText("Nema veze. Tvoji podaci ce se cuvati lokano.")
                    .setConfirmText("OK")

                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.cancel();
                        }
                    })
                    .show();

        }
    }

    private void updateVoznje (VoznjaSimple voznja){

        // Set endpoint
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://projekt001.app.fit.ba/autoskola")
                .build();

        // Generate service
        PrijavaWebService service = restAdapter.create(PrijavaWebService.class);

        // Callback
        Callback<VoznjaSimple> callback = new Callback<VoznjaSimple>() {
            @Override
            public void success(VoznjaSimple prijave, Response response) {
                Log.d("Update voznje - success:", "");
                refreshAktivnePrijave();

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Update voznje - fail:",error.toString());
            }
        };

        // GET request
        service.updateVoznje(voznja, callback);
    }
}
