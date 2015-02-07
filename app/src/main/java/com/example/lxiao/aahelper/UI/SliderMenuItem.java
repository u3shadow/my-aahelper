package com.example.lxiao.aahelper.UI;

/**
 * Created by U3 on 2015/2/8.
 */
public class SliderMenuItem {
    private int id;
    private String title;
    public SliderMenuItem(int pid, String ptext)
    {
        id = pid;
        title = ptext;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
