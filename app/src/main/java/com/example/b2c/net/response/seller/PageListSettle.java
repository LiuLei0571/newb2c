package com.example.b2c.net.response.seller;

import com.example.b2c.net.response.ResponseResult;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用途：已结算实体类
 * Created by yy
 */
public class PageListSettle {
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
    private List<SettleCell> rows;

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<SettleCell> getRows() {
        return rows;
    }
}
