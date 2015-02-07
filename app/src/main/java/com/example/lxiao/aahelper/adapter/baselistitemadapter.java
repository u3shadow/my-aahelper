package com.example.lxiao.aahelper.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by U3 on 2015/2/8.
 */
public  abstract class baselistitemadapter extends BaseAdapter {


    private List mList;
    private Context mcontext;
    private LayoutInflater minflater;
    public baselistitemadapter(List plist, Context pcontext){
        mList = plist;
        mcontext = pcontext;
        minflater = LayoutInflater.from(mcontext);
    }
    public List getlist()
    {
        return mList;
    }
    public LayoutInflater getinflater()
    {
        return minflater;
    }
    public Context getcontext()
    {
        return mcontext;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }




}
