package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 确认订单sample层
 */
public class ShoppingCartDetail implements Serializable{

    @SerializedName("shoppingCartId")
    private int shoppingCartId;
    @SerializedName("sampleId")
    private int sampleId;
    @SerializedName("sampleName")
    private String sampleName;
    @SerializedName("sampleNum")
    private int sampleNum;
    @SerializedName("samplePic")
    private String samplePic;
    @SerializedName("samplePrice")
    private String samplePrice;
    @SerializedName("sampleSKUAmount")
    private int sampleSKUAmount;
    @SerializedName("sampleSKUId")
    private String sampleSKUId;
    @SerializedName("sampleProDetail")
    private String proDetail;

    private boolean isChecked;

    public String getSamplePrice() {
        return samplePrice;
    }

    public void setSamplePrice(String samplePrice) {
        this.samplePrice = samplePrice;
    }

    public int getSampleId() {
        return sampleId;
    }

    public void setSampleId(int sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public int getSampleNum() {
        return sampleNum;
    }

    public void setSampleNum(int sampleNum) {
        this.sampleNum = sampleNum;
    }

    public int getSampleSKUAmount() {
        return sampleSKUAmount;
    }

    public void setSampleSKUAmount(int sampleSKUAmount) {
        this.sampleSKUAmount = sampleSKUAmount;
    }

    public String getSampleSKUId() {
        return sampleSKUId;
    }

    public void setSampleSKUId(String sampleSKUId) {
        this.sampleSKUId = sampleSKUId;
    }

    public String getProDetail() {
        return proDetail;
    }

    public void setProDetail(String proDetail) {
        this.proDetail = proDetail;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getSamplePic() {
        return samplePic;
    }

    public void setSamplePic(String sampleDefaultPic) {
        this.samplePic = sampleDefaultPic;
    }

    public int getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(int shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }
}
