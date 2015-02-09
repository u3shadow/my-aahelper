package com.example.lxiao.aahelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.business.businessuser;
import com.example.lxiao.aahelper.model.User;

import java.util.List;

/**
 * Created by U3 on 2015/2/9.
 */
public class adapteruser extends AdapterBase{

    class host{
        ImageView userpic;
        TextView username;
    }
    private List<User> mlist;
    private host mhost;
    private Context mcontext;
    public adapteruser(Context c) {
        super(c,null);
        businessuser mbusiness = new businessuser(c);
        List list = mbusiness.getnothideuser();
        setlist(list);
        mcontext = c;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = getinflater().inflate(R.layout.useritemlayout, null);
            mhost.username = (TextView)convertView.findViewById(R.id.tv_username);
            mhost.userpic = (ImageView)convertView.findViewById(R.id.iv_userimage);
            convertView.setTag(mhost);
        }
            else{
            mhost = (host)convertView.getTag();
        }
        User _user = (User)getlist().get(position);
        mhost.username.setText(_user.getMusername());
        mhost.userpic.setImageResource(R.drawable.user);
        return convertView;
    }
}
