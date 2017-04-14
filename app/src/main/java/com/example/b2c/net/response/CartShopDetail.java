package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 确认订单详情第一层 CartShopDetail
 *
 *   "shopId": 1072,
 "shopName": "yyseller",
 "shopWarehouseList": 【】
 */
public class CartShopDetail implements Serializable{
    @SerializedName("shopId")
    private int shopId;
    @SerializedName("shopName")
    private String shopName;
    @SerializedName("shopWarehouseList")
    private List<ShopWareHouse> shopWareHouseList;

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

    public List<ShopWareHouse> getShopWareHouseList() {
        return shopWareHouseList;
    }

    public void setShopWareHouseList(List<ShopWareHouse> shopWareHouseList) {
        this.shopWareHouseList = shopWareHouseList;
    }
}
