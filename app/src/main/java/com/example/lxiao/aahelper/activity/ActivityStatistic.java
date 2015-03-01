package com.example.lxiao.aahelper.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.UI.SliderMenuItem;
import com.example.lxiao.aahelper.UI.SliderMenuView;
import com.example.lxiao.aahelper.adapter.adapterbookselect;
import com.example.lxiao.aahelper.baseactivity.ActivityFrame;
import com.example.lxiao.aahelper.business.businessbook;
import com.example.lxiao.aahelper.business.businessstatistic;
import com.example.lxiao.aahelper.model.Book;

/**
 * Created by lxiao on 2015/2/25.
 */
public class ActivityStatistic extends ActivityFrame implements SliderMenuView.OnSlideMenuListener{


    private businessbook mbusinessbook;
    private Book mbook;
    private businessstatistic mbusinessstatistic;
    private TextView mtextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appendmainbody(R.layout.statistic_layout);
        initvar();
        initview();
        initlistener();
        binddata();
        SetTitle();
        createslidemenu(R.array._sstatistic);
    }
    private void SetTitle()
    {
        setTitle(getString(R.string._sstatistic)+"——账本："+mbook.getBookName());
    }
    @Override
    public void initvar() {
        super.initvar();
        mbusinessbook = new businessbook(this);
        mbusinessstatistic = new businessstatistic(this);
        mbook = mbusinessbook.getdefault();
    }

    @Override
    public void initview() {
        super.initview();
        mtextview = (TextView)findViewById(R.id.tv_statistictextview);
    }

    @Override
    public void initlistener() {
        super.initlistener();
    }
    public void binddata()
    {
        showprogressdialog(R.string._statistictitle,R.string._sstatisticwait);
        new binddatathread().start();
    }
    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1)
            {
                String _result = (String)msg.obj;
                mtextview.setText(_result);
                dismissprogressdialog();
            }
        }
    };

    @Override
    public void onSlideMenuItemClick(View pview, SliderMenuItem pslideMenuItem) {
       slideMenuToggle();
        if(pslideMenuItem.getId() == 0)
            showbookselect();
    }

    class binddatathread extends Thread{
        @Override
        public void run() {
            String _result = mbusinessstatistic.getPayOutUserIdByBookId(mbook.getBookId());
            Message _message = new Message();
            _message.obj = _result;
            _message.what = 1;
            mhandler.sendMessage(_message);
        }
    }
    private void showbookselect()//need fix
    {
        AlertDialog.Builder _builder = new AlertDialog.Builder(this);
        View _view = LayoutInflater.from(this).inflate(R.layout.dialog_list, null);
        ListView _listview = (ListView) _view.findViewById(R.id.ListViewSelect);
        adapterbookselect _adapterbookselect = new adapterbookselect(this);
        _listview.setAdapter(_adapterbookselect);

        _builder.setTitle(getString(R.string._sselectbook))

                .setNegativeButton(R.string._sback, null)
                .setView(_view);
        AlertDialog _alertdialog = _builder.create();
        _alertdialog.show();
        _listview.setOnItemClickListener(new onbookitemclicklistener(_alertdialog));
    }

    private class onbookitemclicklistener implements AdapterView.OnItemClickListener {
        private AlertDialog _dialog;

        public onbookitemclicklistener(AlertDialog pdialog) {
            _dialog = pdialog;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mbook = (Book) ((Adapter) parent.getAdapter()).getItem(position);
            binddata();
            _dialog.dismiss();
        }
    }
}
