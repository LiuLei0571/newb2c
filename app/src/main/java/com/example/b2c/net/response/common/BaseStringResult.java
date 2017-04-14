package com.example.b2c.net.response.common;

import com.example.b2c.net.response.ResponseResult;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用途：
 * Created by milk on 17/3/1.
 * 邮箱：649444395@qq.com
 */

public class BaseStringResult {
    @SerializedName("meta")
    private ResponseResult meta;
    @SerializedName("data")
    private List<String> data;

    public ResponseResult getMeta() {
        return meta;
    }

    public void setMeta(ResponseResult meta) {
        this.meta = meta;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
