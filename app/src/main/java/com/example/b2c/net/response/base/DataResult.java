package com.example.b2c.net.response.base;

import com.example.b2c.net.response.ResponseResult;
import com.example.b2c.net.response.seller.LogisticDetail;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用途：
 * Created by milk on 16/11/2.
 * 邮箱：649444395@qq.com
 */

public class DataResult {
    @SerializedName("rows")
    private List<LogisticDetail> rows;
    private ResponseResult result;

    public List<LogisticDetail> getRows() {
        return rows;
    }

    public void setRows(List<LogisticDetail> rows) {
        this.rows = rows;
    }

    public ResponseResult getResult() {
        return result;
    }

    public void setResult(ResponseResult result) {
        this.result = result;
    }
}
