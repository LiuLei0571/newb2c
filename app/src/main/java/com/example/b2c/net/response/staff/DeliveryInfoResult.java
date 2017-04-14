package com.example.b2c.net.response.staff;

import com.google.gson.annotations.SerializedName;

/**
 * 用途：
 * Created by milk on 16/11/18.
 * 邮箱：649444395@qq.com
 */

public class DeliveryInfoResult {
    @SerializedName("deliveryNo")
    private String deliveryNo;
    @SerializedName("orderCode")
    private String orderCode;
    @SerializedName("isFirst")
    private int isFirst;

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(int isFirst) {
        this.isFirst = isFirst;
    }
}
