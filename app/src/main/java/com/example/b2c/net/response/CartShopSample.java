package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车店铺下产品子项
 */
public class CartShopSample implements Serializable{
    @SerializedName("id")
    private int id;//购物车id
    @SerializedName("sampleId")
    private int sampleId;
    @SerializedName("sampleName")
    private String sampleName;
    @SerializedName("num")
    private int sampleNum;
    @SerializedName("inventory")
    private int inventory;
    @SerializedName("samplePic")
    private String samplePic;
    @SerializedName("samplePrice")
    private String samplePrice;
    @SerializedName("sampleSKUAmount")
    private int sampleSKUAmount;
    @SerializedName("sampleSKUId")
    private String sampleSKUId;
    @SerializedName("proDetail")
    private String proDetail;

    @SerializedName("shopWarehouseList")
    private List<ShopWareHouse> shopWareHouseList;

    private boolean  checked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSampleId() {
        return sampleId;
    }

    public void setSampleId(int sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public int getSampleNum() {
        return sampleNum;
    }

    public void setSampleNum(int sampleNum) {
        this.sampleNum = sampleNum;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getSamplePic() {
        return samplePic;
    }

    public void setSamplePic(String samplePic) {
        this.samplePic = samplePic;
    }

    public String getSamplePrice() {
        return samplePrice;
    }

    public void setSamplePrice(String samplePrice) {
        this.samplePrice = samplePrice;
    }

    public int getSampleSKUAmount() {
        return sampleSKUAmount;
    }

    public void setSampleSKUAmount(int sampleSKUAmount) {
        this.sampleSKUAmount = sampleSKUAmount;
    }

    public String getSampleSKUId() {
        return sampleSKUId;
    }

    public void setSampleSKUId(String sampleSKUId) {
        this.sampleSKUId = sampleSKUId;
    }

    public String getProDetail() {
        return proDetail;
    }

    public void setProDetail(String proDetail) {
        this.proDetail = proDetail;
    }

    public List<ShopWareHouse> getShopWareHouseList() {
        return shopWareHouseList;
    }

    public void setShopWareHouseList(List<ShopWareHouse> shopWareHouseList) {
        this.shopWareHouseList = shopWareHouseList;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
