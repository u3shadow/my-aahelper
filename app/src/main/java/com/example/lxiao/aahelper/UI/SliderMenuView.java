package com.example.lxiao.aahelper.UI;

import android.app.Activity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.adapter.ListItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by U3 on 2015/2/8.
 */
public class SliderMenuView {
    private boolean mswitch;
    private List mlist;
    private Activity mActivity;
    private RelativeLayout mrelativelayout;
    private ListView mlistview;
    private OnSlideMenuListener mlistclicklistener;//user to cast activity to interface

    public SliderMenuView(Activity sAcitivity) {
        mActivity = sAcitivity;
        if(mActivity instanceof OnSlideMenuListener)
        mlistclicklistener = (OnSlideMenuListener) sAcitivity;//cast activity witch implement this interface
        initver();
        initview();
        initlistener();
        bindlist();
    }
    public void RemoveBottomBox()

    {
        RelativeLayout _mainlayout  = (RelativeLayout)mActivity.findViewById(R.id.rl_over_all_layout);
        _mainlayout.removeView(mrelativelayout);
        mrelativelayout = null;
    }
    public void initver() {
        mswitch = true;
        mlist = new ArrayList();


    }

    public void initview() {
        mrelativelayout = (RelativeLayout) mActivity.findViewById(R.id.bottomlayout1);

    }

    public void initlistener() {
        mrelativelayout.setOnClickListener(new sliderclicklistener());
        mrelativelayout.setFocusableInTouchMode(true);//if not set key press event dont dispatch to view
        //open or close the menue when menue key pressed
    /*    mrelativelayout.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.v("sk","up!");
                    Toggle();
                }
                return false;
            }
        });*/


    }

    public void Toggle() {
        if (mswitch) {
            open();
        } else
            close();
    }

    public void add(SliderMenuItem pitem) {
        mlist.add(pitem);
    }

    private void bindlist() {
        mlistview = (ListView) mActivity.findViewById((R.id.bottomlistview));
        ListItemAdapter mlistadapter = new ListItemAdapter(mlist, mActivity);
        mlistview.setAdapter(mlistadapter);
        mlistview.setOnItemClickListener(new listitemclicklistener());
    }

    private void onslidemenuclick() {

    }

    private void open() {
        RelativeLayout.LayoutParams mlayoutparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mlayoutparams.addRule(RelativeLayout.BELOW, R.id.headlayout1);
        mrelativelayout.setLayoutParams(mlayoutparams);
        mswitch = !mswitch;
    }

    private void close() {
        RelativeLayout.LayoutParams mlayoutparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mActivity.getResources().getDimensionPixelSize(R.dimen.bottomlistheight));
        mlayoutparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mrelativelayout.setLayoutParams(mlayoutparams);
        mswitch = !mswitch;
    }

    class sliderclicklistener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Toggle();
        }
    }

    class listitemclicklistener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SliderMenuItem mitem = (SliderMenuItem) parent.getItemAtPosition(position);
            mlistclicklistener.onSlideMenuItemClick(view, mitem);//call back listener
        }
    }

    public interface OnSlideMenuListener {
        public abstract void onSlideMenuItemClick(View pview, SliderMenuItem pslideMenuItem);
    }//user to callback , activity implement it
}
