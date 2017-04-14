package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 商品详情
 */
public class SampleDetailResult implements Serializable{

    @SerializedName("sampleId")
    private int sampleId;
    @SerializedName("dollarPrice")//美元价格
    private String dollarPrice;
    @SerializedName("price")//产品当前价格
    private String price;
    @SerializedName("sampleName")
    private String sampleName;
    @SerializedName("isFavorite")
    private int isFavorite;
    @SerializedName("htmlDetail")
    private String htmlDetail;
    @SerializedName("sampleSKU")
    private String sampleSKU;

    @SerializedName("sampleImageList")
    private List<SampleImageDetail> sampleImageList;
    @SerializedName("sampleProList")
    private List<SamplePro> sampleProList;
    @SerializedName("sampleSKUList")
    private List<sampleSKUDetail> sampleSKUList;

    @SerializedName("sellerId")
    private int sellerId;
    @SerializedName("shopId")
    private int shopId;
    @SerializedName("shopName")
    private String shopName;
    @SerializedName("shopLogo")
    private String shopLogo;
    @SerializedName("shopConsistentDescription")
    private float shopConsistentDescription;
    @SerializedName("shopServiceAttitude")
    private float shopServiceAttitude;
    @SerializedName("shopDeliverySpeed")
    private float shopDeliverySpeed;
    @SerializedName("shopRateOfPraise")
    private float shopRateOfPraise;

    @SerializedName("shopWarehouseList")
    private List<ShopDepotResult> shopWarehouseList;

    @SerializedName("sampleDetail")
    private SampleDescription  sampleDescription;


    private ResponseResult result;


    public int getSellerId() {
        return sellerId;
    }


    public float getShopRateOfPraise() {
        return shopRateOfPraise;
    }


    public int getSampleId() {
        return sampleId;
    }

    public void setSampleId(int sampleId) {
        this.sampleId = sampleId;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSampleName() {
        return sampleName;
    }


    public List<SampleImageDetail> getSampleImageList() {
        return sampleImageList;
    }

    public List<SamplePro> getSampleProList() {
        return sampleProList;
    }

    public List<sampleSKUDetail> getSampleSKUList() {
        return sampleSKUList;
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


    public float getShopDeliverySpeed() {
        return shopDeliverySpeed;
    }


    public float getShopConsistentDescription() {
        return shopConsistentDescription;
    }


    public float getShopServiceAttitude() {
        return shopServiceAttitude;
    }


    public String getShopLogo() {
        return shopLogo;
    }

    public ResponseResult getResult() {
        return result;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public String getHtmlDetail() {
        return htmlDetail;
    }

    public String getDollarPrice() {
        return dollarPrice;
    }

    public void setDollarPrice(String dollarPrice) {
        this.dollarPrice = dollarPrice;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public void setHtmlDetail(String htmlDetail) {
        this.htmlDetail = htmlDetail;
    }

    public void setSampleImageList(List<SampleImageDetail> sampleImageList) {
        this.sampleImageList = sampleImageList;
    }

    public void setSampleProList(List<SamplePro> sampleProList) {
        this.sampleProList = sampleProList;
    }

    public void setSampleSKUList(List<sampleSKUDetail> sampleSKUList) {
        this.sampleSKUList = sampleSKUList;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public void setShopConsistentDescription(float shopConsistentDescription) {
        this.shopConsistentDescription = shopConsistentDescription;
    }

    public void setShopServiceAttitude(float shopServiceAttitude) {
        this.shopServiceAttitude = shopServiceAttitude;
    }

    public void setShopDeliverySpeed(float shopDeliverySpeed) {
        this.shopDeliverySpeed = shopDeliverySpeed;
    }

    public void setShopRateOfPraise(float shopRateOfPraise) {
        this.shopRateOfPraise = shopRateOfPraise;
    }

    public List<ShopDepotResult> getShopWarehouseList() {
        return shopWarehouseList;
    }

    public void setShopWarehouseList(List<ShopDepotResult> shopWarehouseList) {
        this.shopWarehouseList = shopWarehouseList;
    }

    public void setResult(ResponseResult result) {
        this.result = result;
    }

    public SampleDescription getSampleDescription() {
        return sampleDescription;
    }

    public void setSampleDescription(SampleDescription sampleDescription) {
        this.sampleDescription = sampleDescription;
    }

    public String getSampleSKU() {
        return sampleSKU;
    }
}

