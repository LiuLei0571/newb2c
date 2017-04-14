package com.example.b2c.net.response.logistics;

import com.google.gson.annotations.SerializedName;

/**
 * 用途：
 * Created by milk on 16/11/29.
 * 邮箱：649444395@qq.com
 */

public class BankDetail {
    @SerializedName("bankCard")
    private String bankCard;
    @SerializedName("accountName")
    private String accountName;
    @SerializedName("bankName")
    private String bankName;
    @SerializedName("messagePhone")
    private String messagePhone;

    public String getMessagePhone() {
        return messagePhone;
    }

    public void setMessagePhone(String messagePhone) {
        this.messagePhone = messagePhone;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
