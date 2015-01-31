package com.autoskola.instruktori.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.autoskola.instruktori.R;

import java.util.List;

/**
 * Created by haris on 1/25/15.
 */
public class MenuDrawerAdapter extends BaseAdapter {
    private Activity activity;
    private List<String> menuItems;

    public MenuDrawerAdapter(Activity activity, List<String> items){
        super();
        this.activity = activity;
        this.menuItems = items;
    }


    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return menuItems.get(position);
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
            convertView = inflater.inflate(R.layout.drawer_menu_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.txtMenuTitle = (TextView) convertView.findViewById(R.id.menu_item_title);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtMenuTitle.setText(menuItems.get(position));

        // Font path
        String fontPath = "fonts/roboto_medium.ttf";

        // Loading Font
        Typeface tf = Typeface.createFromAsset(activity.getAssets(), fontPath);

        // Applying font
        viewHolder.txtMenuTitle.setTypeface(tf);
        return  convertView;
    }

    static class ViewHolder {
        TextView txtMenuTitle;
    }
}
