package com.example.b2c.net.response.base;

import com.example.b2c.net.response.ModelPage;
import com.example.b2c.net.response.ResponseResult;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用途：
 * 作者：Created by john on 2017/2/13.
 * 邮箱：liulei2@aixuedai.com
 */


public class BaseResult  {
    @SerializedName("meta")
    private ResponseResult meta;
    @SerializedName("data")
    private List<ModelPage> data;

    public ResponseResult getMeta() {
        return meta;
    }

    public void setMeta(ResponseResult meta) {
        this.meta = meta;
    }

    public List<ModelPage> getData() {
        return data;
    }

    public void setData(List<ModelPage> data) {
        this.data = data;
    }
}
