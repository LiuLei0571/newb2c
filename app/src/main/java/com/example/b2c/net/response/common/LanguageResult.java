package com.example.b2c.net.response.common;

import com.google.gson.annotations.SerializedName;

/**
 * 用途：
 * Created by milk on 17/2/6.
 * 邮箱：649444395@qq.com
 */

public class LanguageResult {
    @SerializedName("code")
    private String code;
    @SerializedName("name")
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
