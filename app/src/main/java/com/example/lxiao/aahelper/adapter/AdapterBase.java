package com.example.lxiao.aahelper.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.example.lxiao.aahelper.R;


/**
 * Created by U3 on 2015/2/5.
 */
public class AdapterBase extends BaseAdapter {

    class host {
        ImageView mimageview;
        TextView mtextview;
    }

    private String[] textid = new String[6];
    private host mhost;
    private Context mcontext;
  int[] imageid   = new int[]{
            R.drawable.consumption,
            R.drawable.search,
            R.drawable.statistic,
            R.drawable.books,
            R.drawable.type,
            R.drawable.user
    };

    public AdapterBase(Context c) {
        mcontext = c;

        textid[0] = mcontext.getString(R.string._sconsumption);
        textid[1] = mcontext.getString(R.string._ssearch);
        textid[2] = mcontext.getString(R.string._sstatistic);
        textid[3] = mcontext.getString(R.string._sbook);
        textid[4] = mcontext.getString(R.string._stype);
        textid[5] = mcontext.getString(R.string._suser);
    }




    @Override
    public int getCount() {
        return imageid.length;
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
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150,150);
        mhost.mimageview.setLayoutParams(layoutParams);
        mhost.mimageview.setScaleType(ImageView.ScaleType.FIT_XY);
        mhost.mtextview.setText(textid[position]);
        Log.v("sk","adpter running");
        return convertView;
    }


}
