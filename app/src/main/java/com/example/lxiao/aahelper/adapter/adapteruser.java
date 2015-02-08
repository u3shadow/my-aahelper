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
public class adapteruser extends BaseAdapter{

    class host{
        ImageView userpic;
        TextView username;
    }
    private List<User> mlist;
    private host mhost;
    private Context mcontext;
    public adapteruser(Context c) {

        businessuser mbusiness = new businessuser(c);
        List list = mbusiness.getnothideuser();
        setlist(list);
        mcontext = c;
    }
    private void setlist(List plist){
        mlist = plist;
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.useritemlayout,null);
            mhost.username = (TextView)convertView.findViewById(R.id.tv_username);
            mhost.userpic = (ImageView)convertView.findViewById(R.id.iv_userimage);
            convertView.setTag(mhost);
        }
            else{
            mhost = (host)convertView.getTag();
        }
        mhost.username.setText(mlist.get(position).getMusername());
        mhost.userpic.setImageResource(R.drawable.user);
        return convertView;
    }
}
