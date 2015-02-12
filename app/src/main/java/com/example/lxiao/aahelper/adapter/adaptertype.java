package com.example.lxiao.aahelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.business.businesstype;
import com.example.lxiao.aahelper.model.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by U3 on 2015/2/9.
 */
public class adaptertype extends BaseExpandableListAdapter {

    private class GroupHolder{
        TextView Name;
        TextView Count;
    }
    private class ChildHolder{
        TextView Name;
    }
    @Override
    public int getGroupCount() {
        return 0;
    }
    private Context mContext;
    private List mList;
    private businesstype mBusiness;
    private List mChildCountOfGroup;
    public adaptertype(Context pContext)
    {
        mContext = pContext;
        mBusiness = new businesstype(mContext);
        mList = mBusiness.getnothideroottype();
        mChildCountOfGroup = new ArrayList();
    }
    public Integer GetChildCountOfGroup(int pgroupPosition){
        return (Integer) mChildCountOfGroup.get(pgroupPosition);
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        Type _parenttype = (Type)getGroup(groupPosition);
        List _list = mBusiness.getnothidetypebyparentid(_parenttype.getTypeId());

        return _list.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return (Type)mList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Type _group = (Type)getGroup(groupPosition);
        List _list = mBusiness.getnothidetypebyparentid(_group.getTypeId());

        return _list.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return (long)groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
       GroupHolder _GroupHolder;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.typegrouplayout,null);
            _GroupHolder = new GroupHolder();
            _GroupHolder.Name = (TextView)convertView.findViewById(R.id.tv_TypeName);
            _GroupHolder.Count = (TextView)convertView.findViewById(R.id.tv_TypeCount);
            convertView.setTag(_GroupHolder);
        }
        else
        {
            _GroupHolder = (GroupHolder) convertView.getTag();
        }
        Type _group = (Type)getGroup(groupPosition);
        _GroupHolder.Name.setText(_group.getTypeName());
        int _count = mBusiness.getnothidecountbyparentid(_group.getTypeId());
        _GroupHolder.Count.setText(mContext.getString(R.string._sChildTypeCount,new Object[]{_count}));
        mChildCountOfGroup.add(_count);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder _ChildHolder;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.typechildlayout,null);
            _ChildHolder = new ChildHolder();
            _ChildHolder.Name = (TextView)convertView.findViewById(R.id.tv_ChildTypeName);
            convertView.setTag(_ChildHolder);
        }
        else
        {
            _ChildHolder = (ChildHolder) convertView.getTag();
        }
        Type _childtype = (Type)getChild(groupPosition,childPosition);
        _ChildHolder.Name.setText(_childtype.getTypeName());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }



}
