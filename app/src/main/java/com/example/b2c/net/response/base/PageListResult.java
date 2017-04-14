package com.example.b2c.net.response.base;

import com.example.b2c.net.response.ResponseResult;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用途：
 * Created by milk on 16/11/2.
 * 邮箱：649444395@qq.com
 */

public class PageListResult<T> {
    @SerializedName("data")
    private List<T> data;
    private ResponseResult result;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public ResponseResult getResult() {
        return result;
    }

    public void setResult(ResponseResult result) {
        this.result = result;
    }
}
