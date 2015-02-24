package com.autoskola.instruktori.ui.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.autoskola.instruktori.R;
import com.autoskola.instruktori.gps.GpsTask;
import com.autoskola.instruktori.services.model.Prijava;

import java.util.List;

/**
 * Created by The Boss on 18.1.2015.
 */
public class VoznjeOnlineAdapter extends BaseAdapter {

    private Activity activity;
    private List<Prijava> prijaveList;


    public VoznjeOnlineAdapter(Activity activity, List<Prijava> prijaveList) {
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

        if (GpsTask.getInstance().getAktivnaPrijava(activity)!=null){
            if (GpsTask.getInstance().getAktivnaPrijava(activity).VoznjaId.matches(prijaveList.get(position).VoznjaId))
                convertView.setBackgroundColor(activity.getResources().getColor(R.color.turquoise));
            else
                convertView.setBackgroundColor(activity.getResources().getColor(R.color.clouds));
        }
        else
            convertView.setBackgroundColor(activity.getResources().getColor(R.color.clouds));


      /*  if (prijaveList.get(position).getDatumPrijave() != null)
            viewHolder.datum.setText(Helper.parseToFullDate(prijaveList.get(position).getDatumPrijave().toString())); */

        if (prijaveList.get(position).Ime != null || prijaveList.get(position).Prezime != null)
        {
            viewHolder.kandidat.setText(prijaveList.get(position).Ime +" " + prijaveList.get(position).Prezime);
        }

            viewHolder.datum.setText(prijaveList.get(position).getDatumVoznje());

        GpsTask.getInstance().saveVoznjaOffline(prijaveList.get(position),activity);
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
