package com.example.lxiao.aahelper.business;

import android.content.Context;

/**
 * Created by U3 on 2015/2/9.
 * contain some often use method
 */
public class businessbase {
    private Context mcontext;
    protected  businessbase(Context pcontext){
        mcontext = pcontext;
    }
    protected String getstring(int presid)
    {
     return mcontext.getString(presid);
    }
    protected String getstring(int pid,Object[] pobject)
    {
        return mcontext.getString(pid,pobject);
    }
    public Context getcontext()
    {
        return mcontext;
    }
}
