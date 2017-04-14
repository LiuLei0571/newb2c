package com.example.b2c.net.response.logistics;

import com.example.b2c.net.response.base.BaseHasnext;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用途：
 * Created by milk on 16/11/15.
 * 邮箱：649444395@qq.com
 */

public class LogisticsOrderListResult extends BaseHasnext{
    @SerializedName("rows")
    private List<LogisticsOrderDetail> rows;

    public List<LogisticsOrderDetail> getRows() {
        return rows;
    }

    public void setRows(List<LogisticsOrderDetail> rows) {
        this.rows = rows;
    }
}
