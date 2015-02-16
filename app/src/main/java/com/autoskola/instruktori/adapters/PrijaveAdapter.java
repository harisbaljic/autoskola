package com.autoskola.instruktori.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.autoskola.instruktori.R;
import com.autoskola.instruktori.fragments.FragmentPrijave;
import com.autoskola.instruktori.services.PrijavaWebService;
import com.autoskola.instruktori.services.model.Prijava;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by The Boss on 12.1.2015.
 */
public class PrijaveAdapter extends BaseAdapter {

    private Activity activity;
    private List<com.autoskola.instruktori.services.model.Prijava> prijaveList;
    private FragmentPrijave fragmentPrijave;

    public PrijaveAdapter(Activity activity, List<com.autoskola.instruktori.services.model.Prijava> prijaveList,FragmentPrijave fragment) {
        super();
        this.activity = activity;
        this.prijaveList = prijaveList;
        this.fragmentPrijave=fragment;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_item_prijave_inflater, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.datum = (TextView) convertView.findViewById(R.id.list_item_prijava_inflater_datum);
            viewHolder.kandidat = (TextView) convertView.findViewById(R.id.list_item_prijava_inflater_kandidat);
            viewHolder.vrijeme = (TextView) convertView.findViewById(R.id.list_item_prijava_inflater_status);
            viewHolder.napomena = (TextView) convertView.findViewById(R.id.list_item_prijava_inflater_napomena);
            viewHolder.btnOdobri = (Button) convertView.findViewById(R.id.btnOdobriPrijavu);
            viewHolder.btnPonisti = (Button) convertView.findViewById(R.id.btnPonistiPrijavu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String dt = prijaveList.get(position).getDatumVoznje();
        viewHolder.datum.setText(dt);


        String vrijemeOdDo = "";
        if (prijaveList.get(position).getVrijemeVoznje() != null) {
            vrijemeOdDo = " u " + prijaveList.get(position).getVrijemeVoznje();
            viewHolder.vrijeme.setText(vrijemeOdDo);
        }

        if (prijaveList.get(position).getVrijemeVoznje() != null) {
            vrijemeOdDo += " do " + prijaveList.get(position).getVrijemeVoznjeDo().toString();
            viewHolder.vrijeme.append(vrijemeOdDo);
        }


        if (prijaveList.get(position).getNapomena() != null) {
            viewHolder.napomena.setText(prijaveList.get(position).getNapomena());
        }

        if (prijaveList.get(position).getIme() != null || prijaveList.get(position).getPrezime() != null) {
            viewHolder.kandidat.setText(prijaveList.get(position).getIme() + " " + prijaveList.get(position).getPrezime());
        }

        viewHolder.btnOdobri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Odobri");
                //kada klikne odobri da se uradi update da se postavi na aktivno i da se postavi atribut naCekanju da je 0
                Prijava selected = prijaveList.get(position);
                selected.setAktivno(1);
                updatePrijave(selected);
            }
        });

        viewHolder.btnPonisti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Ponisti");
                // a kad poništiš atribut aktivno postaviti na 0, a atribut naCekanju na 0
                Prijava selected = prijaveList.get(position);
                selected.setAktivno(0);
                updatePrijave(selected);

            }
        });
        return convertView;
    }


    @Override
    public int getCount() {
        return prijaveList.size();
    }

    @Override
    public Object getItem(int position) {
        return prijaveList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    private class ViewHolder {
        TextView datum, kandidat, vrijeme, napomena;
        Button btnOdobri, btnPonisti;
    }

    private void updatePrijave(Prijava prijava) {
        // Set endpoint
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://projekt001.app.fit.ba/autoskola")
                .build();

        // Generate service
        PrijavaWebService service = restAdapter.create(PrijavaWebService.class);

        // Callback
        Callback<Prijava> callback = new Callback<com.autoskola.instruktori.services.model.Prijava>() {
            @Override
            public void success(com.autoskola.instruktori.services.model.Prijava prijava, retrofit.client.Response response) {
                Log.d("Update prijave - success:", "");
                 fragmentPrijave.getInstruktor();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Update prijave - fail:", error.toString());
            }
        };

        // GET request
        service.updatePrijave(prijava, callback);
    }
}
