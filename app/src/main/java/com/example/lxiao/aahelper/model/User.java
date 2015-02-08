package com.example.lxiao.aahelper.model;

import java.util.Date;

/**
 * Created by lxiao on 2015/2/8.
 */
public class User {
    private int muserid;
    private String musername;
    private int mdeletestate = -1;

    public int getMuserid() {
        return muserid;
    }

    public void setMuserid(int muserid) {
        this.muserid = muserid;
    }

    public String getMusername() {
        return musername;
    }

    public void setMusername(String musername) {
        this.musername = musername;
    }

    public int getMdeletestate() {
        return mdeletestate;
    }

    public void setMdeletestate(int mdeletestate) {
        this.mdeletestate = mdeletestate;
    }

    public Date getMcreatedate() {
        return mcreatedate;
    }

    public void setMcreatedate(Date mcreatedate) {
        this.mcreatedate = mcreatedate;
    }

    private Date mcreatedate = new Date();
}
