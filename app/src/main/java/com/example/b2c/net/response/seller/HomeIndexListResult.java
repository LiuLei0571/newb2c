package com.example.b2c.net.response.seller;

import com.example.b2c.net.response.OrderSituation;
import com.example.b2c.net.response.ResponseResult;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用途：
 * Created by milk on 16/11/1.
 * 邮箱：649444395@qq.com
 */

public class HomeIndexListResult<T> {
    private ResponseResult result;

    public ResponseResult getResult() {

        return result;
    }

    public void setResult(ResponseResult result) {
        this.result = result;
    }
    @SerializedName("shop")
    private ShopInfoDetail shop;
    @SerializedName("rows")
    private List<OrderSituation> rows;

    public ShopInfoDetail getShop() {
        return shop;
    }

    public void setShop(ShopInfoDetail shop) {
        this.shop = shop;
    }

    public List<OrderSituation> getRows() {
        return rows;
    }

    public void setRows(List<OrderSituation> rows) {
        this.rows = rows;
    }
}
