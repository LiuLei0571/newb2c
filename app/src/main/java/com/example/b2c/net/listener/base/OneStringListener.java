package com.example.b2c.net.listener.base;

/**
 * 用途：
 * Created by milk on 16/11/13.
 * 邮箱：649444395@qq.com
 */

public interface OneStringListener extends RequestfinishListener {

    void onSuccess(String success1);

    void onError(int errorNo ,String errorInfo);
}
