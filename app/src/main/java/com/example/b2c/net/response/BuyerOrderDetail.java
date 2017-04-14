package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 用途：
 * Created by milk on 16/11/3.
 * 邮箱：649444395@qq.com
 */

public class BuyerOrderDetail implements Serializable{
    @SerializedName("id")
    private int orderDetailId;
    @SerializedName("samplePrice")
    private String samplePrice;
    @SerializedName("sampleNum")
    private int sampleNum;
    @SerializedName("sampleImage")
    private String sampleImage;
    @SerializedName("sampleName")
    private String sampleName;
    @SerializedName("sampleId")
    private int sampleId;
    @SerializedName("sampleProperty")
    private String sampleProperty;
    @SerializedName("sampleSKUid")
    private int sampleSKUid;

    public String getSamplePrice() {
        return samplePrice;
    }

    public void setSamplePrice(String samplePrice) {
        this.samplePrice = samplePrice;
    }

    public int getSampleNum() {
        return sampleNum;
    }

    public void setSampleNum(int sampleNum) {
        this.sampleNum = sampleNum;
    }

    public String getSampleImage() {
        return sampleImage;
    }

    public void setSampleImage(String sampleImage) {
        this.sampleImage = sampleImage;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public int getSampleId() {
        return sampleId;
    }

    public void setSampleId(int sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleProperty() {
        return sampleProperty;
    }

    public void setSampleProperty(String sampleProperty) {
        this.sampleProperty = sampleProperty;
    }

    public int getSampleSKUid() {
        return sampleSKUid;
    }

    public void setSampleSKUid(int sampleSKUid) {
        this.sampleSKUid = sampleSKUid;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }
}
