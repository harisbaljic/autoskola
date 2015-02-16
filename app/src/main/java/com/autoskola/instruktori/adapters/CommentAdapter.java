package com.autoskola.instruktori.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.autoskola.instruktori.R;
import com.autoskola.instruktori.helpers.AppController;
import com.autoskola.instruktori.services.model.Komentar;

import java.util.List;

/**
 * Created by haris on 1/31/15.
 */
public class CommentAdapter  extends BaseAdapter {
    private Activity activity;
    private List<Komentar> mListComments;

    public CommentAdapter(Activity activity, List<Komentar> mListComments){
        super();
        this.activity = activity;
        this.mListComments = mListComments;
    }

    @Override
    public int getCount() {
        return mListComments.size();
    }

    @Override
    public Object getItem(int position) {
        return mListComments.get(position);
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
            convertView = inflater.inflate(R.layout.list_item_komentar,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.txtCommentDate = (TextView) convertView.findViewById(R.id.txtCommentDate);
            viewHolder.txtCommentDescription = (TextView) convertView.findViewById(R.id.txtCommentDescription);
            viewHolder.txtCommentTitle = (TextView) convertView.findViewById(R.id.txtCommentTitle);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtCommentDescription.setText(mListComments.get(position).getOpis());
        viewHolder.txtCommentDate.setText(mListComments.get(position).getDatum());

        String firstName =  AppController.getInstance().korisnik.getIme();
        String lastName =  AppController.getInstance().korisnik.getPrezime();
        String firstAndLastName = "";
        if (firstName!=null)
            firstAndLastName+=firstName;

        if (lastName!=null)
            firstAndLastName+=lastName;
        viewHolder.txtCommentTitle.setText(firstAndLastName);
        return  convertView;
    }

    static class ViewHolder {
        TextView txtCommentTitle;
        TextView txtCommentDescription;
        TextView txtCommentDate;
    }
}