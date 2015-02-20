package com.example.lxiao.aahelper.adapter;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.business.businessbook;
import com.example.lxiao.aahelper.model.Book;

import java.util.List;

/**
 * Created by U3 on 2015/2/20.
 */
public class adapterbookselect extends  AdapterBase{
    private class holder{
        ImageView Icon;
        TextView Name;
    }
    public adapterbookselect(Context c, List plist) {
        super(c, plist);
    }
    public adapterbookselect(Context pcontext)
    {
        this(pcontext, null);
        businessbook _businessbook = new businessbook(pcontext);
        List _list = _businessbook.getbooks("");
        setlist(_list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder _Holder;

        if (convertView == null) {
            convertView = getinflater().inflate(R.layout.bookitemlayout, null);
            _Holder = new holder();
            _Holder.Icon = (ImageView)convertView.findViewById(R.id.iv_bookimage);
            _Holder.Name = (TextView)convertView.findViewById(R.id.tv_bookname);
            convertView.setTag(_Holder);
        }
        else {
            _Holder = (holder) convertView.getTag();
        }

        Book _ModelAccountBook = (Book)getItem(position);
        _Holder.Icon.setImageResource(R.drawable.bookicon);
        _Holder.Name.setText(_ModelAccountBook.getBookName());

        return convertView;
    }
}
