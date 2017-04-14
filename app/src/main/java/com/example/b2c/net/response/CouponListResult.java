package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


/**
 * 优惠券列表返回结果
 * Created by yy on 2017/2/10.
 */
public class CouponListResult implements Serializable {

    @SerializedName("rows")
    private List<Coupon> coupons;
    @SerializedName("hasNext")
    private boolean hasNext;

    private ResponseResult responseResult;


    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public ResponseResult getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(ResponseResult responseResult) {
        this.responseResult = responseResult;
    }
}
