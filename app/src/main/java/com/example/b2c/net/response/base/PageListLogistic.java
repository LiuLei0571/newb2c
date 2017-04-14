package com.example.b2c.net.response.base;

import com.example.b2c.net.response.ResponseResult;
import com.example.b2c.net.response.seller.LogisticDetail;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用途：
 * Created by milk on 16/11/1.
 * 邮箱：649444395@qq.com
 */

public class PageListLogistic {
    private ResponseResult result;

    public ResponseResult getResult() {

        return result;
    }

    public void setResult(ResponseResult result) {
        this.result = result;
    }

    @SerializedName("hasNext")
    private boolean hasNext;
    @SerializedName("rows")
    private List<LogisticDetail> rows;

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<LogisticDetail> getRows() {
        return rows;
    }

    public void setRows(List<LogisticDetail> rows) {
        this.rows = rows;
    }
}
