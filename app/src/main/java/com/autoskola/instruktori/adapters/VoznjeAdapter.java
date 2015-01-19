package com.autoskola.instruktori.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.autoskola.instruktori.R;
import com.autoskola.instruktori.model.Prijava;

import java.util.List;

/**
 * Created by The Boss on 18.1.2015.
 */
public class VoznjeAdapter extends BaseAdapter {

    private Activity activity;
    private List<Prijava> prijaveList;


    public VoznjeAdapter(Activity activity, List<Prijava> prijaveList) {
        super();
        this.activity = activity;
        this.prijaveList = prijaveList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_item_dodajvoznju_inflater, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.datum = (TextView) convertView.findViewById(R.id.list_item_prijava_inflater_datum);
            viewHolder.kandidat = (TextView) convertView.findViewById(R.id.list_item_prijava_inflater_kandidat);
            viewHolder.vrijeme = (TextView) convertView.findViewById(R.id.list_item_prijava_inflater_vrijeme);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

      /*  if (prijaveList.get(position).getDatumPrijave() != null)
            viewHolder.datum.setText(Helper.parseToFullDate(prijaveList.get(position).getDatumPrijave().toString())); */

        if (prijaveList.get(position).getIme() != null || prijaveList.get(position).getPrezime() != null)
        {
            viewHolder.kandidat.setText(prijaveList.get(position).getIme() + prijaveList.get(position).getPrezime());
        }

        if (prijaveList.get(position).getVrijemeVoznje() !=null)
        {
            viewHolder.vrijeme.setText(prijaveList.get(position).getVrijemeVoznje() + prijaveList.get(position).getVrijemeVoznje());
        }
        /*if(uplateList.get(position).getDatum() != null)
            viewHolder.datum.setText(uplateList.get(position).getDatum());*/

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
        TextView datum, kandidat,vrijeme;
    }

}
