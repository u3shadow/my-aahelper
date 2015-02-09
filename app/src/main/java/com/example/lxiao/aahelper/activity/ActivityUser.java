package com.example.lxiao.aahelper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.UI.SliderMenuItem;
import com.example.lxiao.aahelper.UI.SliderMenuView;
import com.example.lxiao.aahelper.adapter.adapteruser;
import com.example.lxiao.aahelper.baseactivity.ActivityFrame;

/**
 * Created by U3 on 2015/2/9.
 */
public class ActivityUser extends ActivityFrame implements SliderMenuView.OnSlideMenuListener{

    private ListView lvuserlist;
    private adapteruser madapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       appendmainbody(R.layout.userlayout);
        initvar();
        initview();
        initlistener();
        binddata();
        createslidemenu(R.array._ainituser);
    }
    public void binddata()
    {
     lvuserlist.setAdapter(madapter);
    }
    @Override
    public void initvar() {
        super.initvar();
        madapter = new adapteruser(this);
    }

    @Override
    public void initview() {
        super.initview();
        lvuserlist = (ListView)findViewById(R.id.lv_userlistview);
    }

    @Override
    public void initlistener() {
        super.initlistener();
    }

    @Override
    public void onSlideMenuItemClick(View pview, SliderMenuItem pslideMenuItem) {
        Toast.makeText(this,pslideMenuItem.getTitle(),Toast.LENGTH_LONG).show();
    }
}
