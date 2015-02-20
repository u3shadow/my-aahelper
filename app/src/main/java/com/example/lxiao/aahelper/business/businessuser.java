package com.example.lxiao.aahelper.business;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.lxiao.aahelper.databaseDAL.SQLiteDALuser;
import com.example.lxiao.aahelper.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by U3 on 2015/2/9.
 * to use dal more convenience
 */
public class businessuser extends businessbase {
    private SQLiteDALuser mdal;
    private Context mcontext;

    public businessuser(Context pcontext) {
        super(pcontext);
        mdal = new SQLiteDALuser(pcontext);
        // mcontext = pcontext;
    }

    //Insert user object by SQLiteDALuser insertuser(user) method
    public boolean insert(User puser) {
        return mdal.insertuser(puser);
    }

    //delete user  by id use SQLiteDALuser deleteuser(condition)
    public boolean deleteuserbyid(int pid) {
        String mcondition = "And UserId = " + pid;
        return mdal.deleteuser(mcondition);
    }

    //delete user  by id use SQLiteDALuser updateuser(condition,user)
    public void update(User puser) {
        String mcondition = " UserId = " + puser.getMuserid();
        mdal.updateuser(mcondition, puser);

    }

    //Get user list by SQLiteDALuser getuser(condition) method
    public List<User> getusers(String pcondition) {
        return mdal.getuser(pcondition);
    }

    //Get user  by id use SQLiteDALuser getuser(condition) method
    public User getuserbyid(int id) {
        List<User> mlist = mdal.getuser(" And UserId = " + id);
        if (mlist.size() > 0)
            return mlist.get(0);
        else
            return null;
    }

    //Get user  by id array use SQLiteDALuser getuserbyid(condition) method
    public List<User> getuserlistbyid(String[] id) {
        List<User> mlist = new ArrayList<User>();
        for (int i = 0; i < id.length; i++) {
            User muser = getuserbyid(Integer.valueOf(id[i]));
            mlist.add(muser);
        }
        return mlist;
    }

    //use getuser method to get not deleted user list
    public List<User> getnothideuser() {
        return mdal.getuser(" And DeleteState = -1");
    }
    //if this user is in database
    public boolean isuserexist(String pusername, int puserid)
    {
        String condition = " And UserName = '"+pusername+"'";
        condition += " And UserId <> "+puserid;
        condition += " And DeleteState = -1";
        List<User> _list = getusers(condition);
        if(_list.size() > 0)
            return true;
        else
            return false;
    }
    public boolean hideuserbyid(int pid)
    {
        String condition = " UserId = " + pid;
        ContentValues _value = new ContentValues();
        _value.put("DeleteState","0");
        boolean isupdate = mdal.updateuser(condition,_value);
        return isupdate;
    }
    public String getusernamebyuserid(String puserid)
    {
        List<User> _list = getuserlistbyid(puserid.split(","));
        String _name = "";
        for(int i = 0; i < _list.size();i++)
        {
            _name += _list.get(i).getMusername()+",";
        }
        return _name;
    }
}
