package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

/**
 * 用途：
 * Created by milk on 16/10/31.
 * 邮箱：649444395@qq.com
 */

public class ShopInfoDetail {
    @SerializedName("name")
    private String name;
    @SerializedName("logo")
    private String logo;
    @SerializedName("contactAddress")
    private String contactAddress;
    @SerializedName("hasWareHouse")
    private boolean hasWareHouse;

    public boolean isHasWareHouse() {
        return hasWareHouse;
    }

    public void setHasWareHouse(boolean hasWareHouse) {
        this.hasWareHouse = hasWareHouse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }
}
