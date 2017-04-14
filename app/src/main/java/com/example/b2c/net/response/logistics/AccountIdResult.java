package com.example.b2c.net.response.logistics;

import com.google.gson.annotations.SerializedName;

/**
 * 用途：
 * Created by milk on 16/11/11.
 * 邮箱：649444395@qq.com
 */

public class AccountIdResult {
    @SerializedName("accountId")
    private int accountId;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
