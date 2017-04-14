package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 地址详情
 */
public class ShoppingAddressDetail implements Serializable {


    @SerializedName("id")
    private int id;
    @SerializedName("type")
    private int type;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("provinceName")
    private String provinceName;
    @SerializedName("cityName")
    private String cityName;
    @SerializedName("address")
    private String address;
    @SerializedName("isDefault")
    private int isDefault;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("tel")
    private String tel;
    @SerializedName("provinceCode")
    private String provinceCode;
    @SerializedName("cityCode")
    private String cityCode;
    @SerializedName("userId")
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTel() {
        return tel;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public int getUserId() {
        return userId;

    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShoppingAddressDetail that = (ShoppingAddressDetail) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
    
}
