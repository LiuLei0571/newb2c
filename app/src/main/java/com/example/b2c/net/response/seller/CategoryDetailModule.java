package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用途：
 * 作者：Created by john on 2017/2/14.
 * 邮箱：liulei2@aixuedai.com
 */


public class CategoryDetailModule {
    @SerializedName("propertyId")
    private int propertyId;
    @SerializedName("propertyName")
    private String propertyName;
    @SerializedName("propertyDetails")
    private List<PropertyDetails> propertyDetails;

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public List<PropertyDetails> getPropertyDetails() {
        return propertyDetails;
    }

    public void setPropertyDetails(List<PropertyDetails> propertyDetails) {
        this.propertyDetails = propertyDetails;
    }
}
