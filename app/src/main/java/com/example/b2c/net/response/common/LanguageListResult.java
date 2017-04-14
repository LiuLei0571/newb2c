package com.example.b2c.net.response.common;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用途：
 * Created by milk on 17/2/6.
 * 邮箱：649444395@qq.com
 */

public class LanguageListResult {
    @SerializedName("rows")
    private List<LanguageResult> rows;

    public List<LanguageResult> getRows() {
        return rows;
    }

    public void setRows(List<LanguageResult> rows) {
        this.rows = rows;
    }
}
