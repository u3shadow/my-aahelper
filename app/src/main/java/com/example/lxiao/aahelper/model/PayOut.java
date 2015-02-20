package com.example.lxiao.aahelper.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by U3 on 2015/2/15.
 */
public class PayOut {
    private int PayOutId;
    private int pBookId;
    private String pBookName;
    private int pTypeId;
    private String pTypeName;
    private BigDecimal Amount;
    private Date CreateDate = new Date();
    private int PayWay;

    public int getPayOutId() {
        return PayOutId;
    }

    public void setPayOutId(int payOutId) {
        PayOutId = payOutId;
    }

    public int getpBookId() {
        return pBookId;
    }

    public void setpBookId(int pBookId) {
        this.pBookId = pBookId;
    }

    public String getpBookName() {
        return pBookName;
    }

    public void setpBookName(String pBookName) {
        this.pBookName = pBookName;
    }

    public int getpTypeId() {
        return pTypeId;
    }

    public void setpTypeId(int pTypeId) {
        this.pTypeId = pTypeId;
    }

    public String getpTypeName() {
        return pTypeName;
    }

    public void setpTypeName(String pTypeName) {
        this.pTypeName = pTypeName;
    }

    public BigDecimal getAmount() {
        return Amount;
    }

    public void setAmount(BigDecimal amount) {
        Amount = amount;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    public int getPayWay() {
        return PayWay;
    }

    public void setPayWay(int payWay) {
        PayWay = payWay;
    }

    public int getPayMean() {
        return PayMean;
    }

    public void setPayMean(int payMean) {
        PayMean = payMean;
    }

    public String getPayComment() {
        return PayComment;
    }

    public void setPayComment(String payComment) {
        PayComment = payComment;
    }

    public String getPayUserId() {
        return PayUserId;
    }

    public void setPayUserId(String payUserId) {
        PayUserId = payUserId;
    }

    public String getpPath() {
        return pPath;
    }

    public void setpPath(String pPath) {
        this.pPath = pPath;
    }

    public Date getPayDate() {
        return PayDate;
    }

    public void setPayDate(Date payDate) {
        PayDate = payDate;
    }

    public int getDeleteState() {
        return DeleteState;
    }

    public void setDeleteState(int deleteState) {
        DeleteState = deleteState;
    }

    private int PayMean;
    private String PayComment;
    private String PayUserId;
    private String pPath;
    private Date PayDate;
    private int DeleteState = -1;
}
