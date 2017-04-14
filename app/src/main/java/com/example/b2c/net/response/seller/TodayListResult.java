package com.example.b2c.net.response.seller;

import com.example.b2c.net.response.ResponseResult;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用途：
 * Created by milk on 16/11/1.
 * 邮箱：649444395@qq.com
 */

public class TodayListResult<T> {
    private ResponseResult result;
    @SerializedName("rows")
    private List<T> rows;
    @SerializedName("orderTotalCount")
    private int orderTotalCount;
    @SerializedName("buyerTotalCount")
    private int buyerTotalCount;
    @SerializedName("orderTotalMoney")
    private String orderTotalMoney;
    @SerializedName("sampleTotalCount")
    private int sampleTotalCount;

    public ResponseResult getResult() {
        return result;
    }

    public void setResult(ResponseResult result) {
        this.result = result;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getOrderTotalCount() {
        return orderTotalCount;
    }

    public void setOrderTotalCount(int orderTotalCount) {
        this.orderTotalCount = orderTotalCount;
    }

    public int getBuyerTotalCount() {
        return buyerTotalCount;
    }

    public void setBuyerTotalCount(int buyerTotalCount) {
        this.buyerTotalCount = buyerTotalCount;
    }

    public String getOrderTotalMoney() {
        return orderTotalMoney;
    }

    public void setOrderTotalMoney(String orderTotalMoney) {
        this.orderTotalMoney = orderTotalMoney;
    }

    public int getSampleTotalCount() {
        return sampleTotalCount;
    }

    public void setSampleTotalCount(int sampleTotalCount) {
        this.sampleTotalCount = sampleTotalCount;
    }
}
