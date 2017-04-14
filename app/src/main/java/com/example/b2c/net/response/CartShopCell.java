package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车列表子项
 * Created by yy on 2017/2/12.
 */
public class CartShopCell implements Serializable {

    @SerializedName("shopId")
    private int  shopId;
    @SerializedName("shopName")
    private String   shopName;
    @SerializedName("shoppingCartList")
    private List<CartShopSample> samples;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<CartShopSample> getSamples() {
        return samples;
    }

    public void setSamples(List<CartShopSample> samples) {
        this.samples = samples;
    }
}
