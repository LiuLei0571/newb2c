package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

/**
 * 用途：
 * Created by milk on 16/11/3.
 * 邮箱：649444395@qq.com
 */

public class AssetsDetailResult {
    @SerializedName("canWithdraw")
    private String canWithdraw;
    @SerializedName("freezeFunds")
    private String freezeFunds;
    @SerializedName("bankCard")
    private String bankCard;

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getCanWithdraw() {
        return canWithdraw;
    }

    public void setCanWithdraw(String canWithdraw) {
        this.canWithdraw = canWithdraw;
    }

    public String getFreezeFunds() {
        return freezeFunds;
    }

    public void setFreezeFunds(String freezeFunds) {
        this.freezeFunds = freezeFunds;
    }
}
