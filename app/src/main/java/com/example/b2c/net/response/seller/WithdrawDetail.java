package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

/**
 * 用途：
 * Created by milk on 16/11/4.
 * 邮箱：649444395@qq.com
 */

public class WithdrawDetail {
    @SerializedName("money")
    private String money;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("status")
    private int status;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
