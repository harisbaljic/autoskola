package com.autoskola.instruktori.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.autoskola.instruktori.R;
import com.autoskola.instruktori.helpers.AppController;
import com.autoskola.instruktori.helpers.Helper;
import com.autoskola.instruktori.model.Prijava;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by The Boss on 12.1.2015.
 */
public class PrijaveAdapter extends BaseAdapter {

    private Activity activity;
    private List<Prijava> prijaveList;


    public PrijaveAdapter(Activity activity, List<Prijava> prijaveList) {
        super();
        this.activity = activity;
        this.prijaveList = prijaveList;
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

        if (prijaveList.get(position).getVrijemeVoznjeDo() != null) {
            vrijemeOdDo += " do " + prijaveList.get(position).getVrijemeVoznjeDo().toString();
            viewHolder.vrijeme.append(vrijemeOdDo);
        }


        if (prijaveList.get(position).getNapomena() != null) {
            viewHolder.napomena.setText(prijaveList.get(position).getNapomena());
        }

        if (prijaveList.get(position).getIme() != null || prijaveList.get(position).getPrezime() != null) {
            viewHolder.kandidat.setText(prijaveList.get(position).getIme() + " " + prijaveList.get(position).getPrezime());
        }


        final Button btnOdobri = (Button) convertView.findViewById(R.id.list_item_prijava_inflater_btn_odobri);

        btnOdobri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "Ušao u on click");

                final StringRequest updatePrijave = new StringRequest(Request.Method.POST, Helper.prijavaUpdate, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        Toast.makeText(
                                activity,
                                "Vožnja odobrena",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {


                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("voznjaId", 1 + "");

                        return params;
                    }
                };

                AppController.getInstance().addToRequestQueue(updatePrijave);
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
        Button odobri;
    }


}
