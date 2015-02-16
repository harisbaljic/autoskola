package com.autoskola.instruktori.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.autoskola.instruktori.R;
import com.autoskola.instruktori.helpers.Helper;
import com.autoskola.instruktori.services.model.Obavijest;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by The Boss on 27.12.2014.
 */
public class ObavijestiAdapter extends BaseAdapter {
    private Activity activity;
    private List<Obavijest> obavijestList;

    public ObavijestiAdapter(Activity activity, List<Obavijest> obavijestList){
        super();
        this.activity = activity;
        this.obavijestList = obavijestList;
    }


    @Override
    public int getCount() {
        return obavijestList.size();
    }

    @Override
    public Object getItem(int position) {
        return obavijestList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null)
        {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_item_obavijesti_inflater,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.datum = (TextView) convertView.findViewById(R.id.list_item_obavijesti_inflater_datum);
            viewHolder.naslov = (TextView) convertView.findViewById(R.id.list_item_obavijesti_inflater_naslov);
            viewHolder.tekst = (TextView) convertView.findViewById(R.id.list_item_obavijesti_inflater_tekst);
            viewHolder.slika = (ImageView) convertView.findViewById(R.id.list_item_obavijesti_inflater_slika);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (obavijestList.get(position).getDatumObjave()!=null && obavijestList.get(position).getDatumObjave().getDate()!=null){
            viewHolder.datum.setText(Helper.parseDateForObavijesti(obavijestList.get(position).getDatumObjave().getDate()));
        }

        if(obavijestList.get(position).getNaslov() != null)
            viewHolder.naslov.setText(obavijestList.get(position).getNaslov());
        if(obavijestList.get(position).getSadrzaj() != null)
            viewHolder.tekst.setText(obavijestList.get(position).getSadrzaj());

        // http://stackoverflow.com/questions/22330772/why-use-android-picasso-library-to-download-images
        Picasso.with(activity).load("http://projekt001.app.fit.ba"+obavijestList.get(position).getPutanjaSlika()).into(viewHolder.slika);

        return  convertView;
    }

    static class ViewHolder {
        ImageView slika;
        TextView datum,naslov,tekst;
    }
}
