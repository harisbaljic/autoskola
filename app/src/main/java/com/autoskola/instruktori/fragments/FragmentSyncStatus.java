package com.autoskola.instruktori.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.autoskola.instruktori.R;
import com.autoskola.instruktori.adapters.SyncStatusAdapter;
import com.autoskola.instruktori.services.model.Prijava;
import com.autoskola.instruktori.services.model.Voznja;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by haris on 1/31/15.
 */
public class FragmentSyncStatus extends Fragment {


    private ListView list;
    private List<Prijava> items = new ArrayList<Prijava>();
    private SyncStatusAdapter adapter;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dodaj_voznju, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list = (ListView) getActivity().findViewById(R.id.activity_main_list_voznje);
        getAllOfflineVoznje();
    }

    private void showProgress() {
        if (mProgressDialog != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressDialog.show();
                }
            });

    }

    private void hideProgress() {
        if (mProgressDialog != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressDialog.hide();
                }
            });

    }

    private void getAllOfflineVoznje() {
        new Thread(new Runnable() {
            public void run() {

                Realm realm = Realm.getInstance(getActivity());
                final RealmResults<Voznja> voznjaList = realm.where(Voznja.class)
                        .notEqualTo("voznjaId", "")
                        .findAll();

                adapter = new SyncStatusAdapter(getActivity(), voznjaList);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                });

            }
        }).start();

    }
}
