package com.example.lxiao.aahelper.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.UI.SliderMenuItem;
import com.example.lxiao.aahelper.UI.SliderMenuView;
import com.example.lxiao.aahelper.adapter.adapterbookselect;
import com.example.lxiao.aahelper.adapter.adapterpayout;
import com.example.lxiao.aahelper.baseactivity.ActivityFrame;
import com.example.lxiao.aahelper.business.businessbook;
import com.example.lxiao.aahelper.business.businesspayout;
import com.example.lxiao.aahelper.model.Book;
import com.example.lxiao.aahelper.model.PayOut;

/**
 * Created by U3 on 2015/2/22.
 */
public class ActivityPayOut extends ActivityFrame implements SliderMenuView.OnSlideMenuListener{
    private businesspayout mbusinesspayout;
    private businessbook mbusinessbook;
    private Book mbook;
    private adapterpayout madapterpayout;
    private ListView mpayoutlist;
    private PayOut mselectpayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appendmainbody(R.layout.payoutlayout);
        initvar();
        initview();
        initlistener();
        binddata();
        createslidemenu(R.array._spayout);
    }

    @Override
    public void initvar() {
        mbusinesspayout = new businesspayout(ActivityPayOut.this);
        mbusinessbook = new businessbook(ActivityPayOut.this);
        mbook = mbusinessbook.getdefault();
        madapterpayout = new adapterpayout(ActivityPayOut.this, mbook.getBookId());
    }

    @Override
    public void initview() {
        mpayoutlist = (ListView) findViewById(R.id.lv_payoutlist);
    }

    @Override
    public void initlistener() {
        registerForContextMenu(mpayoutlist);
    }

    public void binddata() {
        adapterpayout _adapterpayout = new adapterpayout(this, mbook.getBookId());
        mpayoutlist.setAdapter(_adapterpayout);
        SetTitle();
    }

    public void SetTitle() {
        int _count = mpayoutlist.getCount();
        String _title = getString(R.string._spayouttitle, new Object[]{mbook.getBookName(), _count});
        setTitle(_title);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo _info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        ListAdapter _listadapter = (ListAdapter)mpayoutlist.getAdapter();
        mselectpayout = (PayOut)_listadapter.getItem(_info.position);
        menu.setHeaderTitle(mselectpayout.getpBookName());
        CreateMenu(menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 1:
                Intent _intent = new Intent(this, ActivityPayOutEditorAdd.class);
                _intent.putExtra("mpayout", mselectpayout);
                startActivityForResult(_intent, 1);
                break;
            case 2:
                delete(mselectpayout);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
    public void delete(PayOut ppayout)
    {
        String _message = getString(R.string._spayoutdelete,new Object[]{ppayout.getpBookName()});
        showalertdialog(getString(R.string._sdeletepayout),_message,new ondeletelistener());
    }

    @Override
    public void onSlideMenuItemClick(View pview, SliderMenuItem pslideMenuItem) {
        slideMenuToggle();
        if(pslideMenuItem.getId() == 0)
        {
            showbookselectdialog();

        }
    }
    public void showbookselectdialog()
    {
        AlertDialog.Builder _builder = new AlertDialog.Builder(this);
        View _view = LayoutInflater.from(this).inflate(R.layout.dialog_list,null);
        ListView _listview = (ListView)_view.findViewById(R.id.ListViewSelect);
        adapterbookselect _bookselectadapter = new adapterbookselect(this);
        _listview.setAdapter(_bookselectadapter);

        _builder.setTitle(getString(R.string._sselectbook))
                .setNegativeButton(getString(R.string._sback),null)
                .setView(_view);
        AlertDialog _alertdialog = _builder.create();
        _alertdialog.show();
        _listview.setOnItemClickListener(new onbookitemclicklistener(_alertdialog));
    }
class onbookitemclicklistener implements ListView.OnItemClickListener{
    private AlertDialog malertdialog;

    public onbookitemclicklistener(AlertDialog palertdialog){
        malertdialog = palertdialog;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mbook = (Book)((Adapter)parent.getAdapter()).getItem(position);
        binddata();
        malertdialog.dismiss();

    }
}
    class ondeletelistener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            boolean _result = mbusinesspayout.deletepayoutbypayoutid(mselectpayout.getPayOutId());
            if(_result)
            {
                binddata();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        binddata();
        super.onActivityResult(requestCode, resultCode, data);

    }

}
