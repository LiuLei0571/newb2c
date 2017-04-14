package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

/**
 * 用途：
 * Created by milk on 16/11/4.
 * 邮箱：649444395@qq.com
 */

public class OrderDetailLogistic {
    @SerializedName("id")
    private int id;
    @SerializedName("place")
    private String place;
    @SerializedName("deliveryCompanyName")
    private String deliveryCompanyName;
    @SerializedName("createTime")
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDeliveryCompanyName() {
        return deliveryCompanyName;
    }

    public void setDeliveryCompanyName(String deliveryCompanyName) {
        this.deliveryCompanyName = deliveryCompanyName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
