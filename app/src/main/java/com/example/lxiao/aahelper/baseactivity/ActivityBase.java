package com.example.lxiao.aahelper.baseactivity;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by U3 on 2015/2/5.
 */
public class ActivityBase extends Activity{
    public void showmsg(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
    public void openactivity(Class<?> activity)
    {
        Intent mintent = new Intent(this,activity);
        startActivity(mintent);
    }
}
