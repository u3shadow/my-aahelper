package com.example.lxiao.aahelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by lxiao on 2015/2/9.
 */
public class AdapterBase extends BaseAdapter{




    private Context mcontext;
    private List mlist;
    public AdapterBase(Context c,List plist) {
        mcontext = c;
        setlist(plist);
    }
    public List getlist(){return mlist;}
    public void setlist(List plist)
    {
        mlist = plist;
    }
    public Context getcontext(){
        return mcontext;
    }
    public LayoutInflater getinflater(){
        return LayoutInflater.from(mcontext);
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }




}
