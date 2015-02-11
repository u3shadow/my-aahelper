package com.example.lxiao.aahelper.model;

import java.util.Date;

/**
 * Created by lxiao on 2015/2/11.
 */
public class Book {
    private String BookName;
    private int BookId;
    private int DeleteState = -1;
    private int IsDefault = -1;
    private Date CreateDate = new Date();

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public int getBookId() {
        return BookId;
    }

    public void setBookId(int bookId) {
        BookId = bookId;
    }

    public int getDeleteState() {
        return DeleteState;
    }

    public void setDeleteState(int deleteState) {
        DeleteState = deleteState;
    }

    public int getIsDefault() {
        return IsDefault;
    }

    public void setIsDefault(int isDefault) {
        IsDefault = isDefault;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }
}
