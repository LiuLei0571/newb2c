package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用途：
 * Created by milk on 16/11/4.
 * 邮箱：649444395@qq.com
 */

public class OrderDetailLogiscticResult {
    @SerializedName("rows")
    private List<OrderDetailLogistic> rows;

    public List<OrderDetailLogistic> getRows() {
        return rows;
    }

    public void setRows(List<OrderDetailLogistic> rows) {
        this.rows = rows;
    }
}
