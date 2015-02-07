package com.example.lxiao.aahelper.UI;

import android.app.Activity;
import android.view.View;
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

    public SliderMenuView(Activity sAcitivity) {
        mActivity = sAcitivity;
        initver();
        initview();
        initlistener();
        bindlist();
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

    }

    private void Toggle() {
        if (mswitch) {
            open();
        } else
            close();
    }

    public void add(SliderMenuItem pitem) {
        mlist.add(pitem);
    }

    private void bindlist() {
        mlistview = (ListView)mActivity.findViewById((R.id.bottomlistview));
        ListItemAdapter mlistadapter = new ListItemAdapter(mlist,mActivity);
        mlistview.setAdapter(mlistadapter);
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
        RelativeLayout.LayoutParams mlayoutparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,mActivity.getResources().getDimensionPixelSize(R.dimen.bottomlistheight));
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
    class listitemclicklistener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

        }
    }
}
