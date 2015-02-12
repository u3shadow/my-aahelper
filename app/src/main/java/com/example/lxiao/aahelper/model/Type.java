package com.example.lxiao.aahelper.model;

import java.util.Date;

/**
 * Created by lxiao on 2015/2/12.
 */
public class Type {
    private int TypeId;
    private String TypeName;
    private int DeleteState = -1;
    private int ParentId = 0;
    private String path;
    private String flag;
    private Date CreateDate = new Date();
    public int getTypeId() {
        return TypeId;
    }

    public void setTypeId(int typeId) {
        TypeId = typeId;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public int getDeleteState() {
        return DeleteState;
    }

    public void setDeleteState(int deleteState) {
        DeleteState = deleteState;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int parentId) {
        ParentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }


}
