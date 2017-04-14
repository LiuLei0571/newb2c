package com.example.b2c.net.response.logistics;

import com.google.gson.annotations.SerializedName;

/**
 * 用途：
 * Created by milk on 16/11/16.
 * 邮箱：649444395@qq.com
 */

public class DataItem {
    @SerializedName("date")
    private String date;
     @SerializedName("dateStatus")
    private int dateStatus;
    @SerializedName("name")
    private String name;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDateStatus() {
        return dateStatus;
    }

    public void setDateStatus(int dateStatus) {
        this.dateStatus = dateStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
