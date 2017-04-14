package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

/**
 * 用途：
 * 作者：Created by john on 2017/2/16.
 * 邮箱：liulei2@aixuedai.com
 */


public class BuyerInfos {
    @SerializedName("buyerName")
    private String buyerName;
    @SerializedName("buyerMobile")
    private String buyerMobile;
    @SerializedName("receiveAddress")
    private String receiveAddress;
    @SerializedName("payAddress")
    private String payAddress;

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerMobile() {
        return buyerMobile;
    }

    public void setBuyerMobile(String buyerMobile) {
        this.buyerMobile = buyerMobile;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getPayAddress() {
        return payAddress;
    }

    public void setPayAddress(String payAddress) {
        this.payAddress = payAddress;
    }
}
