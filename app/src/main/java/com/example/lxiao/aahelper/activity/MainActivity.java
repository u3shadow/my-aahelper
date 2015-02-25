package com.example.lxiao.aahelper.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.lxiao.aahelper.UI.SliderMenuItem;
import com.example.lxiao.aahelper.UI.SliderMenuView;
import com.example.lxiao.aahelper.adapter.AdapterBaseTest;
import com.example.lxiao.aahelper.baseactivity.ActivityFrame;
import com.example.lxiao.aahelper.R;

import static android.widget.AdapterView.OnItemClickListener;


public class MainActivity extends ActivityFrame implements SliderMenuView.OnSlideMenuListener {
    private GridView mgridview;
    private AdapterBaseTest madapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.over_all_layout);
        appendmainbody(R.layout.centergridlayout);
        initvar();
        initview();
        initlistener();
        binddata();
    }
    public void initvar(){
        madapter = new AdapterBaseTest(this);
    }
    public void initview(){
        mgridview  = (GridView)findViewById(R.id.centergridview);
        createslidemenu(R.array._amainlistmenu);
    }

    @Override
    public void initlistener() {
        super.initlistener();
        mgridview.setOnItemClickListener(new onappgridclicklistener());
    }

    private void binddata(){

        mgridview.setAdapter(madapter);
        Log.v("sk", "bind data");

    }


    @Override
    public void onSlideMenuItemClick(View pview, SliderMenuItem pslideMenuItem) {
        Toast.makeText(this,pslideMenuItem.getTitle(),Toast.LENGTH_LONG).show();
    }
    private class onappgridclicklistener implements OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String _menuname = (String)parent.getAdapter().getItem(position);
            if(_menuname.equals(getString(R.string._suser)))
            {
                openactivity(ActivityUser.class);
            return;
            }
            if(_menuname.equals(getString(R.string._sbook)))
            {
                openactivity(ActivityBooks.class);
                return;
            }
            if(_menuname.equals(getString(R.string._stype)))
            {
                openactivity(ActivityType.class);
                return ;
            }
            if(_menuname.equals(getString(R.string._sconsumption)))
            {
                openactivity(ActivityPayOutEditorAdd.class);
                return ;
            }
            if(_menuname.equals(getString(R.string._ssearch)))
            {
                openactivity(ActivityPayOut.class);
                return ;
            }

            if(_menuname.equals(getString(R.string._sstatistic)))
            {
                openactivity(ActivityStatistic.class);
                return ;
            }
        }
    }
}
