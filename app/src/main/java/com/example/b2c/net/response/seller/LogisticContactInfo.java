package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 快递公司联系人信息
 * Created by Administrator on 2017/2/5.
 */
public class LogisticContactInfo implements Serializable {
    @SerializedName("contactFirstName")
    private String contactFirstName;
    @SerializedName("contactLastName")
    private String contactLastName;
    @SerializedName("contactEmail")
    private String contactEmail;
    @SerializedName("contactMobile")
    private String contactMobile;
    @SerializedName("contactTel")
    private String contactTel;

    public String getContactFirstName() {
        return contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public String getContactTel() {
        return contactTel;
    }
}
