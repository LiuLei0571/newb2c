package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用途：
 * 作者：Created by john on 2017/2/14.
 * 邮箱：liulei2@aixuedai.com
 */


public class CategoryDetailResult {
    @SerializedName("rows")
    private List<CategoryDetailModule> rows;

    public List<CategoryDetailModule> getRows() {
        return rows;
    }

    public void setRows(List<CategoryDetailModule> rows) {
        this.rows = rows;
    }
}
