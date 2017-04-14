package com.example.b2c.net.response.logistics;

import com.google.gson.annotations.SerializedName;

/**
 * 用途：
 * Created by milk on 16/11/16.
 * 邮箱：649444395@qq.com
 */

public class TotalPriceResult {
    @SerializedName("totalPrice")
    private String totalPrice;
    @SerializedName("dataCount")
    private int dataCount;

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
