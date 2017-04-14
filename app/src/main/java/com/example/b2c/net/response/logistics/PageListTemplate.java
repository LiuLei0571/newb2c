package com.example.b2c.net.response.logistics;

import com.example.b2c.net.response.ResponseResult;
import com.example.b2c.net.response.seller.FreightTemplate;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用途：
 * Created by yy
 */

public class PageListTemplate {
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
    private List<FreightTemplate> rows;

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<FreightTemplate> getRows() {
        return rows;
    }

    public void setRows(List<FreightTemplate> rows) {
        this.rows = rows;
    }
}
