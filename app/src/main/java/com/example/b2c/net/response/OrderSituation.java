package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * 用途：
 * Created by milk on 16/11/1.
 * 邮箱：649444395@qq.com
 */

public class OrderSituation {
    @SerializedName("name")
    private String name;
    @SerializedName("count")
    private int count;
    @SerializedName("status")
    private int status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
