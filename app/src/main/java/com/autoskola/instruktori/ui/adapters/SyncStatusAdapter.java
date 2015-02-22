package com.autoskola.instruktori.ui.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.autoskola.instruktori.R;
import com.autoskola.instruktori.gps.GpsTask;
import com.autoskola.instruktori.services.model.Voznja;

import java.util.List;

/**
 * Created by haris on 1/31/15.
 */
public class SyncStatusAdapter extends BaseAdapter {

    private Activity activity;
    private List<Voznja> voznjaList;


    public SyncStatusAdapter(Activity activity, List<Voznja> voznjeList) {
        super();
        this.activity = activity;
        this.voznjaList = voznjeList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_sync_status_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtFirstLastName);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (GpsTask.getInstance().getAktivnaPrijava(activity)!=null){
            if (GpsTask.getInstance().getAktivnaPrijava(activity).VoznjaId.matches(voznjaList.get(position).getVoznjaId()))
                convertView.setBackgroundColor(activity.getResources().getColor(R.color.turquoise));
            else
                convertView.setBackgroundColor(activity.getResources().getColor(R.color.clouds));
        }
        else
            convertView.setBackgroundColor(activity.getResources().getColor(R.color.clouds));

       viewHolder.txtDate.setText(voznjaList.get(position).getDate());
       viewHolder.txtName.setText(voznjaList.get(position).getIme() +" "+voznjaList.get(position).getPrezime());
        return convertView;
    }


    @Override
    public int getCount() {
        return voznjaList.size();
    }

    @Override
    public Object getItem(int position) {
        return voznjaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    private class ViewHolder {
        TextView txtDate,txtStatus,txtName;
    }
}