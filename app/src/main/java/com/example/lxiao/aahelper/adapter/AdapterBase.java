package com.example.lxiao.aahelper.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.example.lxiao.aahelper.R;


/**
 * Created by U3 on 2015/2/5.
 */
public class AdapterBase implements ListAdapter {
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    class host {
        ImageView mimageview;
        TextView mtextview;
    }

    private String[] textid;
    private host mhost;
    private Context mcontext;
  int[] imageid;/*   = new int[]{
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5,
            R.drawable.pic6
    };*/

    public AdapterBase(Context c) {
        textid = new String[6];
        mcontext = c;

    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return textid[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.gridiconlayout, null);
            mhost = new host();
            mhost.mimageview = (ImageView) convertView.findViewById(R.id.iv_iconimage);
            mhost.mtextview = (TextView) convertView.findViewById(R.id.tv_icontext);
            convertView.setTag(mhost);
        } else {
           mhost = (host) convertView.getTag();
        }
        mhost.mimageview.setImageResource(imageid[position]);
        mhost.mtextview.setText(textid[position]);
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
