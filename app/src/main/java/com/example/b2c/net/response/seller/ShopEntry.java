package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 店铺信息实体内容
 * Created by yy on 2017/1/23.
 */
public class ShopEntry implements Serializable {

    @SerializedName("loginName")
    private String loginName;//用户名
    @SerializedName("shopId")
    private int shopId;//店铺id
    @SerializedName("shopName")
    private String shopName;//店铺名称
    @SerializedName("logo")
    private String logo;//店铺logo
    @SerializedName("rateOfPraise")
    private String rateOfPraise;//好评率
    @SerializedName("consistentDescription")
    private String consistentDescription;//描述相符度
    @SerializedName("serviceAttitude")
    private String serviceAttitude;//服务态度
    @SerializedName("deliverySpeed")
    private String deliverySpeed;//发货速度
    @SerializedName("introduction")
    private String introduction;//店铺介绍
    @SerializedName("goodsSource")
    private String  goodsSource;//货物来源

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getRateOfPraise() {
        return rateOfPraise;
    }

    public void setRateOfPraise(String rateOfPraise) {
        this.rateOfPraise = rateOfPraise;
    }

    public String getConsistentDescription() {
        return consistentDescription;
    }

    public void setConsistentDescription(String consistentDescription) {
        this.consistentDescription = consistentDescription;
    }

    public String getServiceAttitude() {
        return serviceAttitude;
    }

    public void setServiceAttitude(String serviceAttitude) {
        this.serviceAttitude = serviceAttitude;
    }

    public String getDeliverySpeed() {
        return deliverySpeed;
    }

    public void setDeliverySpeed(String deliverySpeed) {
        this.deliverySpeed = deliverySpeed;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getGoodsSource() {
        return goodsSource;
    }

    public void setGoodsSource(String goodsSource) {
        this.goodsSource = goodsSource;
    }

    @Override
    public String toString() {
        return "ShopEntry{" +
                "loginName='" + loginName + '\'' +
                ", shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", logo='" + logo + '\'' +
                ", rateOfPraise='" + rateOfPraise + '\'' +
                ", consistentDescription='" + consistentDescription + '\'' +
                ", serviceAttitude='" + serviceAttitude + '\'' +
                ", deliverySpeed='" + deliverySpeed + '\'' +
                ", introduction='" + introduction + '\'' +
                ", goodsSource='" + goodsSource + '\'' +
                '}';
    }
}
