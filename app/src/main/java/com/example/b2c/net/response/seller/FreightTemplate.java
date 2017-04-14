package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 运费模板
 * Created by yy on 2017/2/5.
 */
public class FreightTemplate implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("shippingProvinceCode")
    private String shippingProvinceCode;//发货地省份名code
    @SerializedName("shippingProvinceName")
    private String shippingProvinceName;//发货地省份名
    @SerializedName("shippingCityCode")
    private String shippingCityCode;//发货地城市名
    @SerializedName("shippingCityName")
    private String shippingCityName;//发货地城市名
    @SerializedName("receiveProvinceCode")
    private String receiveProvinceCode;//接收人省份名称
    @SerializedName("receiveProvinceName")
    private String receiveProvinceName;//接收人省份名称
    @SerializedName("receiveCityCode")
    private String receiveCityCode;//接收人省份名称
    @SerializedName("receiveCityName")
    private String receiveCityName;//接收人城市名称
    @SerializedName("weight")
    private String weight;//重量
    @SerializedName("deliveryTimeCost")
    private String deliveryTimeCost;//天数
    @SerializedName("fee")
    private String fee;//运费

    public int getId() {
        return id;
    }

    public String getShippingProvinceName() {
        return shippingProvinceName;
    }

    public String getShippingCityName() {
        return shippingCityName;
    }

    public String getReceiveProvinceName() {
        return receiveProvinceName;
    }

    public String getReceiveCityName() {
        return receiveCityName;
    }

    public String getWeight() {
        return weight;
    }

    public String getDeliveryTimeCost() {
        return deliveryTimeCost;
    }

    public String getFee() {
        return fee;
    }
}
