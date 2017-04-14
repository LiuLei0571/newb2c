package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 确认订单选择运费
 * Created by yy on 2017/2/10.
 */
//    "deliveryCompanyId": 259,
//    "deliveryCompanyName": null,
//    "deliveryFee": 200,
//    "deliveryCostTime": 7
public class DeliveryFeeDetail implements Serializable {

    @SerializedName("deliveryCompanyId")
    private int deliveryCompanyId;
    @SerializedName("deliveryCompanyName")
    private String  deliveryCompanyName;
    @SerializedName("deliveryFee")
    private String  deliveryFee;
    @SerializedName("deliveryCostTime")
    private int  deliveryCostTime;
    @SerializedName("checked")
    private boolean  checked;

    public int getDeliveryCompanyId() {
        return deliveryCompanyId;
    }

    public void setDeliveryCompanyId(int deliveryCompanyId) {
        this.deliveryCompanyId = deliveryCompanyId;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public int getDeliveryCostTime() {
        return deliveryCostTime;
    }

    public void setDeliveryCostTime(int deliveryCostTime) {
        this.deliveryCostTime = deliveryCostTime;
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getDeliveryCompanyName() {
        return deliveryCompanyName;
    }

    public void setDeliveryCompanyName(String deliveryCompanyName) {
        this.deliveryCompanyName = deliveryCompanyName;
    }
}
