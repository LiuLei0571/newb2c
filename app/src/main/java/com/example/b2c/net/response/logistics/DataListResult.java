package com.example.b2c.net.response.logistics;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用途：
 * Created by milk on 16/11/16.
 * 邮箱：649444395@qq.com
 */

public class DataListResult {
    @SerializedName("rows")
    private List<DataItem> rows;

    public List<DataItem> getRows() {
        return rows;
    }

    public void setRows(List<DataItem> rows) {
        this.rows = rows;
    }
}
