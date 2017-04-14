package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

/**
 * 用途：
 * Created by milk on 16/11/2.
 * 邮箱：649444395@qq.com
 */

public class MoneyDetail {
    @SerializedName("funds")
    private String funds;
    @SerializedName("freezeFunds")
    private String freezeFunds;

    public String getFunds() {
        return funds;
    }

    public void setFunds(String funds) {
        this.funds = funds;
    }

    public String getFreezeFunds() {
        return freezeFunds;
    }

    public void setFreezeFunds(String freezeFunds) {
        this.freezeFunds = freezeFunds;
    }
}
