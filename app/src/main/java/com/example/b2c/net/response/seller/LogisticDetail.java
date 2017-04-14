package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 用途：
 * Created by milk on 16/11/1.
 * 邮箱：649444395@qq.com
 */

public class LogisticDetail implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("corperateStatus")
    private boolean corperateStatus;
    @SerializedName("deliveryName")
    private String deliveryName;
    @SerializedName("companyName")
    private String companyName;
    @SerializedName("introduction")
    private String introduction;
    @SerializedName("isDefault")
    private int isDefault;
    @SerializedName("type")
    private int type;
    @SerializedName("logo")
    private String logo;

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCorperateStatus() {
        return corperateStatus;
    }

    public void setCorperateStatus(boolean corperateStatus) {
        this.corperateStatus = corperateStatus;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCompanyName() {
        return companyName;
    }
}
