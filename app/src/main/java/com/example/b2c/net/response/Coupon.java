package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * 优惠券实体类
 * Created by yy on 2017/2/10.
 */
public class Coupon implements Serializable {

    @SerializedName("id")
    private int id;
    /**
     * 0：现金券
     * 1：折扣券
     */
    @SerializedName("type")
    private int type;
    @SerializedName("name")
    private String name;
    @SerializedName("checked")
    private boolean  checked;
    @SerializedName("discount")
    private String  discount;
    /**
     *1：是 0：否
     *是否永久有效
     */
    @SerializedName("isForever")
    private int isForever;
    /***
     * 是否过期
     *1：是 0：否
     */
    @SerializedName("isExpired")
    private int isExpired;
    /***
     * 是否已使用
     *1：是 0：否
     * @return
     */
    @SerializedName("isHasUsed")
    private int isHasUsed;
    @SerializedName("expiredDate")
    private long expiredDate;
    @SerializedName("createTime")
    private long createTime;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsForever() {
        return isForever;
    }

    public void setIsForever(int isForever) {
        this.isForever = isForever;
    }

    public int getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(int isExpired) {
        this.isExpired = isExpired;
    }

    public int getIsHasUsed() {
        return isHasUsed;
    }

    public void setIsHasUsed(int isHasUsed) {
        this.isHasUsed = isHasUsed;
    }

    public long getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(long expiredDate) {
        this.expiredDate = expiredDate;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
