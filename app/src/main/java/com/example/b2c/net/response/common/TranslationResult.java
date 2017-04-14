package com.example.b2c.net.response.common;

import com.google.gson.annotations.SerializedName;

/**
 * 用途：
 * 作者：Created by john on 2017/2/9.
 * 邮箱：liulei2@aixuedai.com
 */


public class TranslationResult {
    @SerializedName("value")
    private String value;
    @SerializedName("text")
    private String text;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
