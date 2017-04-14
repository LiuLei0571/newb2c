package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 卖家公司信息
 * Created by yy on 2017/1/18.
 */

public class SellerCompanyEntry  implements Serializable{
    @SerializedName("type")
    private int type;//类型
    @SerializedName("sex")
    private int sex;//性别
    @SerializedName("birthDate")
    private String birthDate;//出生日期
    @SerializedName("idCard")
    private String idCard;//身份证号
    @SerializedName("corporateFrontIdCardPic")
    private String corporateFrontIdCardPic;//身份证正面图片
    @SerializedName("corporateBackIdCardPic")
    private String corporateBackIdCardPic;//身份证背面图片

    @SerializedName("companyName")
    private String companyName;//商家公司名
    @SerializedName("regAddress")
    private String regAddress;//商家注册地址
    @SerializedName("regDate")
    private String regDate;//商家注册时间
    @SerializedName("totalPersonNum")
    private int totalPersonNum;//商家总人数
    @SerializedName("briefIntro")
    private String briefIntro;//公司简介
    @SerializedName("businessLicenseAttachment")
    private String businessLicense;//营业执照附件
    @SerializedName("businessScope")
    private String businessScope;//经营范围

    @SerializedName("firstName")
    private String  firstName;//联系人名
    @SerializedName("lastName")
    private String  lastName;//联系人姓
    @SerializedName("contactTel")
    private String  contactTel;//联系人电话
    @SerializedName("contactMobile")
    private String  contactMobile;//联系人手机
    @SerializedName("contactEmail")
    private String  contactEmail;//联系人Email
    @SerializedName("contactProvinceCode")
    private String  contactProvinceCode;//商家联系地址省份编号
    @SerializedName("contactCityCode")
    private String  contactCityCode;//商家联系地址城市编号
    @SerializedName("contactProvinceName")
    private String  contactProvinceName;//商家联系地址省份编号名称
    @SerializedName("contactCityName")
    private String  contactCityName;//商家联系地址城市编号名称
    @SerializedName("contactAddress")//联系地址
    private String  contactAddress;
    @SerializedName("bankName")
    private String  bankName ;//开户银行名称
    @SerializedName("bankBranchName")
    private String  bankBranchName;//开户银行支行名称
    @SerializedName("bankAccountName")
    private String  bankAccountName;//银行开户名
    @SerializedName("bankAccount")
    private String  bankAccount;//银行账号
    @SerializedName("auditStatus")
    private int auditStatus;//审核状态
    @SerializedName("auditFeedback")
    private String  auditFeedback;//审核反馈
    /**
     * 10：商家信息提交
     * 20：公司负责人信息提交
     * 30：网站运营信息提交
     * 40：结算账号信息提交
     * 50：入住经营信息提交
     * 0 : 还未填写过任何信息
     */
    @SerializedName("step")
    private int step;//提交步骤

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCorporateFrontIdCardPic() {
        return corporateFrontIdCardPic;
    }

    public void setCorporateFrontIdCardPic(String corporateFrontIdCardPic) {
        this.corporateFrontIdCardPic = corporateFrontIdCardPic;
    }

    public String getCorporateBackIdCardPic() {
        return corporateBackIdCardPic;
    }

    public void setCorporateBackIdCardPic(String corporateBackIdCardPic) {
        this.corporateBackIdCardPic = corporateBackIdCardPic;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public int getTotalPersonNum() {
        return totalPersonNum;
    }

    public void setTotalPersonNum(int totalPersonNum) {
        this.totalPersonNum = totalPersonNum;
    }

    public String getBriefIntro() {
        return briefIntro;
    }

    public void setBriefIntro(String briefIntro) {
        this.briefIntro = briefIntro;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactProvinceCode() {
        return contactProvinceCode;
    }

    public void setContactProvinceCode(String contactProvinceCode) {
        this.contactProvinceCode = contactProvinceCode;
    }

    public String getContactCityCode() {
        return contactCityCode;
    }

    public void setContactCityCode(String contactCityCode) {
        this.contactCityCode = contactCityCode;
    }

    public String getContactProvinceName() {
        return contactProvinceName;
    }

    public void setContactProvinceName(String contactProvinceName) {
        this.contactProvinceName = contactProvinceName;
    }

    public String getContactCityName() {
        return contactCityName;
    }

    public void setContactCityName(String contactCityName) {
        this.contactCityName = contactCityName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public int getAuditStatus() {
        return auditStatus;
    }

    public String getAuditFeedback() {
        return auditFeedback;
    }
}
