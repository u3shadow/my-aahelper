package com.example.lxiao.aahelper.baseactivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.lxiao.aahelper.R;

import java.lang.reflect.Field;

/**
 * Created by U3 on 2015/2/5.
 */
public class ActivityBase extends Activity{
    private ProgressDialog mprogressdialog ;

    public void showmsg(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
    public void openactivity(Class<?> activity)
    {
        Intent mintent = new Intent(this,activity);
        startActivity(mintent);
    }
    public void initvar()
    {}//init var
    public void initview(){}//init view
    public void initlistener(){}//init listener
    //control dialog close or open
    public void SetDialogIsClose(DialogInterface pDialog, Boolean pisClose)
    {
        try {
            Field _Field = pDialog.getClass().getSuperclass().getDeclaredField("mShowing");
            _Field.setAccessible(true);
            _Field.set(pDialog,pisClose);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
    public AlertDialog showalertdialog(String ptitle,String mmessage,DialogInterface.OnClickListener mlistener)
    {
        return new AlertDialog.Builder(this)
                .setTitle(ptitle)
                .setMessage(mmessage)
                .setNeutralButton(getString(R.string._sdeleteuser),mlistener)
                .setNegativeButton(getString(R.string._scanceldeletebutton),null)
                .show();
    }
    public void showprogressdialog(int ptitleid, int pmessageid)
    {
        mprogressdialog = new ProgressDialog(this);
        mprogressdialog.setTitle(getString(ptitleid));
        mprogressdialog.setMessage(getString(pmessageid));
        mprogressdialog.show();
    }
    public void dismissprogressdialog()
    {
        mprogressdialog.dismiss();
    }
}
