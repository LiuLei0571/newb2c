package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetMyOrderResult {
    @SerializedName("rows")
    private List<BuyerOrderList> rows;
    @SerializedName("hasNext")
    protected boolean hasNext;
    private ResponseResult result;

    public List<BuyerOrderList> getRows() {
        return rows;
    }

    public void setRows(List<BuyerOrderList> rows) {
        this.rows = rows;
    }

    public ResponseResult getResult() {
        return result;
    }

    public void setResult(ResponseResult result) {
        this.result = result;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
}
