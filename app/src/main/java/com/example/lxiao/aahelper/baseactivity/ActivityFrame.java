package com.example.lxiao.aahelper.baseactivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.UI.SliderMenuItem;
import com.example.lxiao.aahelper.UI.SliderMenuView;


public class ActivityFrame extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.over_all_layout);

    }
   public void appendmainbody(int presId)
   {
       LinearLayout _smain_body = (LinearLayout)findViewById(R.id.maincenterlayout);
       View _view = LayoutInflater.from(this).inflate(presId,null);
       RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
      _smain_body.addView(_view,layoutParams);
       Log.v("sk", "appendview");
   }
   public void createslidemenu(int menuid)
   {
       SliderMenuView iview = new SliderMenuView(this);
       String[] mlist = getResources().getStringArray(menuid);
       for(int i = 0;i < mlist.length; i++)
       {
           SliderMenuItem mitem = new SliderMenuItem(i, mlist[i]);
           iview.add(mitem);
       }
   }
}
