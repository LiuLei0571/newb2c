package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 结算信息cell
 * Created by yy on 2017/2/7.
 */

public class SettleCell implements Serializable{

    @SerializedName("orderId")
    private int orderId;
    @SerializedName("type")
    private int type;
    @SerializedName("code")
    private String code;
    @SerializedName("deliveryNo")
    private String deliveryNo;
    @SerializedName("totalPrice")
    private String totalPrice;
    @SerializedName("deliveryFee")
    private String deliveryFee;
    @SerializedName("platActualTransferMoney")
    private String yetPay;
    @SerializedName("remainderPrice")
    private String waitPay;
    @SerializedName("createTime")
    private long createTime;

    public int getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getYetPay() {
        return yetPay;
    }

    public String getWaitPay() {
        return waitPay;
    }


    public int getOrderId() {
        return orderId;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public void setYetPay(String yetPay) {
        this.yetPay = yetPay;
    }

    public void setWaitPay(String waitPay) {
        this.waitPay = waitPay;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
