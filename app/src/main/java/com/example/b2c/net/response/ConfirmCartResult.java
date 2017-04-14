package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 确认订单详情
 */
public class ConfirmCartResult implements Serializable{
    //收货地址
    @SerializedName("shoppingAddress")
    private ShoppingAddressDetail shoppingAddress;
    //收款地址
    @SerializedName("payAddress")
    private ShoppingAddressDetail payAddress;
    //cartShop集合
    @SerializedName("cartShopList")
    private List<CartShopDetail> cartShopList;
    //优惠券集合
    @SerializedName("couponList")
    private List<Coupon> couponList;
    //所有商品总价不含运费不含优惠
    @SerializedName("totalOrdersPrice")
    private String totalOrdersPrice;


    private ResponseResult result;

    public ShoppingAddressDetail getShoppingAddress() {
        return shoppingAddress;
    }

    public void setShoppingAddress(ShoppingAddressDetail shoppingAddress) {
        this.shoppingAddress = shoppingAddress;
    }

    public List<CartShopDetail> getCartShopList() {
        return cartShopList;
    }

    public void setCartShopList(List<CartShopDetail> cartShopList) {
        this.cartShopList = cartShopList;
    }

    public ResponseResult getResult() {
        return result;
    }

    public void setResult(ResponseResult result) {
        this.result = result;
    }

    public List<Coupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Coupon> couponList) {
        this.couponList = couponList;
    }

    public ShoppingAddressDetail getPayAddress() {
        return payAddress;
    }

    public void setPayAddress(ShoppingAddressDetail payAddress) {
        this.payAddress = payAddress;
    }

    public String getTotalOrdersPrice() {
        return totalOrdersPrice;
    }

    public void setTotalOrdersPrice(String totalOrdersPrice) {
        this.totalOrdersPrice = totalOrdersPrice;
    }
}
