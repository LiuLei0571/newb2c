package com.example.b2c.net.response.staff;

import com.google.gson.annotations.SerializedName;

/**
 * 用途：
 * 作者：Created by john on 2017/2/16.
 * 邮箱：liulei2@aixuedai.com
 */


public class ExpressInfo {
    @SerializedName("loginName")
    private String loginName;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("email")
    private String email;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
