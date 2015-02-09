package com.example.lxiao.aahelper.business;

import android.content.Context;
import android.util.Log;

import com.example.lxiao.aahelper.databaseDAL.SQLiteDALuser;
import com.example.lxiao.aahelper.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by U3 on 2015/2/9.
 */
public class businessuser extends businessbase{
    private SQLiteDALuser mdal;
    private Context mcontext;

    public businessuser(Context pcontext) {
        super(pcontext);
        mdal = new SQLiteDALuser(pcontext);
       // mcontext = pcontext;
    }

    public boolean insert(User puser) {
        return mdal.insertuser(puser);
    }

    public boolean deleteuserbyid(int pid) {
        String mcondition = "And UserId = " + pid;
        return mdal.deleteuser(mcondition);
    }

    public void update(User puser) {
        String mcondition = " UserId = " + puser.getMuserid();
        mdal.updateuser(mcondition, puser);

    }
    public List<User> getusers(String pcondition)
    {
     return  mdal.getuser(pcondition);
    }
    public User getuserbyid(int id)
    {
       List<User> mlist = mdal.getuser( " And UserId = "+id);
        if(mlist.size() > 0)
            return mlist.get(0);
        else
            return null;
    }
    public List<User> getuserlistbyid(String[] id)
    {
        List<User> mlist = new ArrayList<User>();
        for(int i = 0;i < id.length;i++){
        User muser =  getuserbyid(Integer.getInteger(id[i]));
           mlist.add(muser);
        }
        return mlist;
    }
    public List<User> getnothideuser()
    {
        List<User> asdf = mdal.getuser(" And DeleteState = 1");
        if(asdf.isEmpty())
        {
            Log.v("sk", "Empty");
        }
        return asdf;
    }
}
