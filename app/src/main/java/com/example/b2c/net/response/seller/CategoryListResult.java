package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用途：
 * 作者：Created by john on 2017/2/14.
 * 邮箱：liulei2@aixuedai.com
 */


public class CategoryListResult {
    @SerializedName("rows")
    private List<CategoryModule> rows;

    public List<CategoryModule> getRows() {
        return rows;
    }

    public void setRows(List<CategoryModule> rows) {
        this.rows = rows;
    }
}
