package com.example.lxiao.aahelper.mainactivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.example.lxiao.aahelper.baseactivity.ActivityFrame;
import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.adapter.AdapterBase;


public class MainActivity extends ActivityFrame {
    private GridView mgridview;
    private AdapterBase madapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.over_all_layout);
        appendmainbody(R.layout.centergridlayout);
        initvar();
        initview();
        binddata();
    }
    private void initvar(){
        madapter = new AdapterBase(this);
    }
    private void initview(){
        mgridview  = (GridView)findViewById(R.id.centergridview);
        createslidemenu(R.array._amainlistmenu);
    }
    private void binddata(){

        mgridview.setAdapter(madapter);
        Log.v("sk", "bind data");

    }


}
