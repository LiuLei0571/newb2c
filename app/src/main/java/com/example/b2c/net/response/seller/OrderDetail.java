package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 用途：
 * Created by milk on 16/11/3.
 * 邮箱：649444395@qq.com
 */

public class OrderDetail implements Serializable {
    @SerializedName("price")
    private String price;
    @SerializedName("amount")
    private int amount;
    @SerializedName("totalPrice")
    private String totalPrice;
    @SerializedName("specification")
    private String specification;
    @SerializedName("sampleImage")
    private String sampleImage;
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getSampleImage() {
        return sampleImage;
    }

    public void setSampleImage(String sampleImage) {
        this.sampleImage = sampleImage;
    }
}
