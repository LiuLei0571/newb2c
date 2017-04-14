package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * version 3.0
 * Created by yy on 2017/1/10.
 * 用户协议实体
 */
public class ProtocolInfo {

    @SerializedName("title")
    private String title;
    @SerializedName("text")
    private String text;
    @SerializedName("code")
    private String code;

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getCode() {
        return code;
    }
}
