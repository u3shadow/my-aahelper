package com.example.lxiao.aahelper.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.business.businessbook;
import com.example.lxiao.aahelper.model.Book;

import java.util.List;

/**
 * Created by U3 on 2015/2/9.
 */
public class adapterbook extends AdapterBase {

    class host {
        ImageView bookicon;
        TextView bookname;

    }

    private List<Book> mlist;
    private host mhost;
    private Context mcontext;

    public adapterbook(Context c) {
        super(c, null);
        businessbook mbusiness = new businessbook(c);
        List list = mbusiness.getnothidebook();
        setlist(list);
        mcontext = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            mhost = new host();
            convertView = getinflater().inflate(R.layout.bookitemlayout, null);
            mhost.bookname = (TextView) convertView.findViewById(R.id.tv_bookname);
            mhost.bookicon = (ImageView) convertView.findViewById(R.id.iv_bookimage);
            convertView.setTag(mhost);
        } else {
            mhost = (host) convertView.getTag();
        }
        Book _book = (Book) getlist().get(position);
        mhost.bookname.setText(_book.getBookName());
        if (_book.getIsDefault() == 1)
            mhost.bookicon.setImageResource(R.drawable.defaultbookicon);
        else
            mhost.bookicon.setImageResource(R.drawable.bookicon);
        return convertView;
    }
}
