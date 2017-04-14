package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 买家,卖家实体信息
 * Created by yy on 2017/1/15.
 */
public class BuyerInfo implements Serializable {

    @SerializedName("userId")
    private int userId;
    @SerializedName("loginName")
    private String loginName;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("sex")
    private int sex;
    @SerializedName("headPic")
    private String headPic;
    @SerializedName("birthDate")
    private String birthDate;
    @SerializedName("email")
    private String email;
    @SerializedName("tel")
    private String tel;

    public int getUserId() {
        return userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getSex() {
        return sex;
    }

    public String getHeadPic() {
        return headPic;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
