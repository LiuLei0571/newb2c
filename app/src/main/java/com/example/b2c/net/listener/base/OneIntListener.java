package com.example.b2c.net.listener.base;

/**
 * 用途：
 * Created by milk on 16/11/9.
 * 邮箱：649444395@qq.com
 */

public interface OneIntListener extends RequestfinishListener {
    void onSuccess(int id);

    void onError(int errorNO,String errorInfo);
}
