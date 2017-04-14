package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 生成订单返回值
 */
public class MakeOrderResult {
    @SerializedName("orderIdList")
    private List<Integer> orderIdList;

    private ResponseResult result;


    public List<Integer> getOrderIdList() {
        return orderIdList;
    }

    public void setOrderIdList(List<Integer> orderIdList) {
        this.orderIdList = orderIdList;
    }

    public ResponseResult getResult() {
        return result;
    }

    public void setResult(ResponseResult result) {
        this.result = result;
    }


}
