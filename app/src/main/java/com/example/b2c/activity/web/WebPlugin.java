package com.example.b2c.activity.web;

import com.alibaba.fastjson.JSONObject;
import com.example.b2c.activity.common.WebViewActivity;

/**
 * 用途：
 * Created by milk on 16/9/5.
 * 邮箱：649444395@qq.com
 */
public class WebPlugin {
    private WebViewActivity oldWebViewActivity;

    public WebPlugin(WebViewActivity oldWebViewActivity) {
        this.oldWebViewActivity = oldWebViewActivity;
    }
    private String getString(JSONObject jsonObject, String key){
        try{
            return jsonObject.getString(key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
