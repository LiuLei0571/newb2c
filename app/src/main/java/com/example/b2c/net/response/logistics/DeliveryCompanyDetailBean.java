package com.example.b2c.net.response.logistics;

import java.io.Serializable;

/**
 * 物流管理员我的首页的信息
 */

public class DeliveryCompanyDetailBean implements Serializable{
    private String DeliveryName;
    private String CompanyName;
    private String RegAddress;
    private String RegDate;
    private int TotalPersonNum;

    public String getDeliveryName() {
        return DeliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        DeliveryName = deliveryName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getRegAddress() {
        return RegAddress;
    }

    public void setRegAddress(String regAddress) {
        RegAddress = regAddress;
    }

    public String getRegDate() {
        return RegDate;
    }

    public void setRegDate(String regDate) {
        RegDate = regDate;
    }

    public int getTotalPersonNum() {
        return TotalPersonNum;
    }

    public void setTotalPersonNum(int totalPersonNum) {
        TotalPersonNum = totalPersonNum;
    }

    public int getIntroduction() {
        return Introduction;
    }

    public void setIntroduction(int introduction) {
        Introduction = introduction;
    }

    public int getBusinessLicenseAttachment() {
        return BusinessLicenseAttachment;
    }

    public void setBusinessLicenseAttachment(int businessLicenseAttachment) {
        BusinessLicenseAttachment = businessLicenseAttachment;
    }

    private int Introduction;
    private int BusinessLicenseAttachment;
}
