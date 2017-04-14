package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 店铺仓库实体
 * Created by yy on 2017/2/8.
 */

public class ShopDepotResult implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("provinceName")
    private String provinceName;
    @SerializedName("cityName")
    private String cityName;
    @SerializedName("address")
    private String address;

    private boolean checked;

    public int getId() {
        return id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
