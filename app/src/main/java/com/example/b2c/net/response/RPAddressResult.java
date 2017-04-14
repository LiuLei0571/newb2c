package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 买家下单获取收货收款地址返回结果
 * Created by yy on 2017/2/14.
 */
public class RPAddressResult  implements Serializable{
    @SerializedName("shoppingAddress")
    private ShoppingAddressDetail  shoppingAddress;
    @SerializedName("payAddress")
    private ShoppingAddressDetail  payAddress;

    public ShoppingAddressDetail getShoppingAddress() {
        return shoppingAddress;
    }

    public void setShoppingAddress(ShoppingAddressDetail shoppingAddress) {
        this.shoppingAddress = shoppingAddress;
    }

    public ShoppingAddressDetail getPayAddress() {
        return payAddress;
    }

    public void setPayAddress(ShoppingAddressDetail payAddress) {
        this.payAddress = payAddress;
    }
}
