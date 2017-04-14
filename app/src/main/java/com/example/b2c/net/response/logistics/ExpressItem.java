package com.example.b2c.net.response.logistics;

import com.google.gson.annotations.SerializedName;

/**
 * 用途：
 * Created by milk on 16/11/15.
 * 邮箱：649444395@qq.com
 */

public class ExpressItem {
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("place")
    private String place;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
