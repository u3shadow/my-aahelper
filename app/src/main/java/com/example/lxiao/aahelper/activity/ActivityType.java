package com.example.lxiao.aahelper.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.UI.SliderMenuItem;
import com.example.lxiao.aahelper.UI.SliderMenuView;
import com.example.lxiao.aahelper.adapter.adaptertype;
import com.example.lxiao.aahelper.baseactivity.ActivityFrame;
import com.example.lxiao.aahelper.business.businesstype;
import com.example.lxiao.aahelper.model.Type;
import com.example.lxiao.aahelper.utility.RegexTools;

import java.io.Serializable;
import java.util.List;

/**
 * Created by U3 on 2015/2/9.
 */
public class ActivityType extends ActivityFrame implements SliderMenuView.OnSlideMenuListener {

    private ExpandableListView eplistview;
    private adaptertype madapter;
    private businesstype mbusiness;
    private Type mtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appendmainbody(R.layout.typelayout);
        initvar();
        initview();
        initlistener();
        binddata();
        createslidemenu(R.array._atypelistmenu);
    }

    private void SetTitle() {
        int _count = mbusiness.getnothidecount();
        String _title = getString(R.string._stypetitle, new Object[]{_count});
        setTitle(_title);
    }

    public void binddata() {
        madapter = new adaptertype(this);
        eplistview.setAdapter(madapter);
        SetTitle();
    }

    @Override
    public void initvar() {
        super.initvar();

        mbusiness = new businesstype(this);
    }

    @Override
    public void initview() {
        super.initview();
        eplistview = (ExpandableListView) findViewById(R.id.el_typeview);
    }

    @Override
    public void initlistener() {

        registerForContextMenu(eplistview);
    }

    //长按弹出菜单 need prove
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;
        long _position = info.packedPosition;
        int _groupposition = ExpandableListView.getPackedPositionGroup(_position);
        int _type = ExpandableListView.getPackedPositionType(_position);
        switch (_type) {
            case ExpandableListView.PACKED_POSITION_TYPE_GROUP:
                mtype = (Type) madapter.getGroup((int)_position);
                break;
            case ExpandableListView.PACKED_POSITION_TYPE_CHILD:
                int _childposition = ExpandableListView.getPackedPositionChild(_position);
                mtype = (Type) madapter.getChild((int) _position, _childposition);
                break;
            default:
                break;

        }
        //menu.setHeaderIcon();
           if(mtype != null)
           {
               menu.setHeaderTitle(mtype.getTypeName());
           }
        CreateMenu(menu);
      /*  p_ContextMenu.add(0, 3, 3, R.string.ActivityCategoryTotal);
        if(mAdapterCategory.GetChildCountOfGroup(_GroupPosition) != 0 && mSelectModelCategory.GetParentID() == 0)
        {
            p_ContextMenu.findItem(2).setEnabled(false);
        }*/ //??????
    }

    //弹出菜单点击 need prove
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Intent _intent = new Intent(ActivityType.this,ActivityTypeEditorAdd.class);
                _intent.putExtra("mtype",mtype);
                this.startActivityForResult(_intent,1);
                break;
            case 2:
                deletetype(mtype); //need improve
                break;
            case 3:
               /* List<Type> _list = mbusiness.getnothidetypebyparentid(mtype.getTypeId());
               Intent _intent = new Intent();
                _intent.putExtra("Total",(Serializable)_list);
                _intent.setClass(this,ActivityTypeChart.class);
                startActivity(_intent);*/ //about chart
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    //删除提示框
    public void deletetype(Type ptype) {
        String _msg = getString(R.string._sdeletetypetitle, new Object[]{ptype.getTypeName()});
        showalertdialog(getString(R.string._sdeletetype), _msg, new ondeletetypelistener(ptype));
    }

    //处理删除事件
    class ondeletetypelistener implements DialogInterface.OnClickListener {
        private Type mtype;
        public ondeletetypelistener(Type ptype)
        {
            mtype = ptype;
        }
        @Override
        public void onClick(DialogInterface dialog, int which) {
            boolean isdeleted = mbusiness.hidetypebypath(mtype.getPath());
            if (isdeleted) {
                binddata();
                return;
            } else
                Toast.makeText(ActivityType.this, getString(R.string._deletefail), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        binddata();
        super.onActivityResult(requestCode, resultCode, data);
    }

    //根据点击项来选择是否弹出对话框
    @Override
    public void onSlideMenuItemClick(View pview, SliderMenuItem pslideMenuItem) {
       slideMenuToggle();
        if(pslideMenuItem.getId() == 0)
        {

            Intent _intent = new Intent(this,ActivityTypeEditorAdd.class);
            this.startActivityForResult(_intent,1);
        }
        if(pslideMenuItem.getId() == 1)
        {
            //some char thing here;
        }
    }

}


