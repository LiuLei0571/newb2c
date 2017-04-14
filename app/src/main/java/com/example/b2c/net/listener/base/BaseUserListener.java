package com.example.b2c.net.listener.base;

import com.example.b2c.net.response.Users;

/**
 * 用途：
 * Created by milk on 16/11/9.
 * 邮箱：649444395@qq.com
 */

public interface BaseUserListener extends RequestfinishListener {
    //成功回调
    void onSuccess(Users user, String errorInfo);
    //失败回调
    void onError(int errorNo,String errorInfo);
}
