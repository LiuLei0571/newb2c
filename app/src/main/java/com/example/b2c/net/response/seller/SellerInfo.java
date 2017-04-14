package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

/**
 * 用途：
 * 作者：Created by john on 2017/2/16.
 * 邮箱：liulei2@aixuedai.com
 */


public class SellerInfo {
    @SerializedName("sellerName")
    private String sellerName;
    @SerializedName("sellerMobile")
    private String sellerMobile;
    @SerializedName("shippingAddress")
    private String shippingAddress;

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerMobile() {
        return sellerMobile;
    }

    public void setSellerMobile(String sellerMobile) {
        this.sellerMobile = sellerMobile;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

}
