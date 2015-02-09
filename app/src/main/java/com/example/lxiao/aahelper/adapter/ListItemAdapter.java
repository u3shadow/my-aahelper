package com.example.lxiao.aahelper.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.UI.SliderMenuItem;

import java.util.List;

/**
 * Created by U3 on 2015/2/8.
 */
public  class ListItemAdapter extends baselistitemadapter {


    public ListItemAdapter(List plist, Context pcontext) {
        super(plist, pcontext);
    }

    class host {
        TextView mtextview;
    }
    private host mhost;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getinflater().inflate(R.layout.listmenuitemlayout, null);
            mhost = new host();
            mhost.mtextview = (TextView) convertView.findViewById(R.id.tv_listmenutext);
            convertView.setTag(mhost);
        } else {
            mhost = (host) convertView.getTag();
        }
        SliderMenuItem mitem = (SliderMenuItem)getlist().get(position);
        mhost.mtextview.setText(mitem.getTitle());
        //Log.v("sk", "adpter running");
        return convertView;
    }


}
