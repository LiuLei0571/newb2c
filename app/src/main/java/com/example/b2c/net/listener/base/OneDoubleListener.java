package com.example.b2c.net.listener.base;

/**
 * 用途：
 * Created by milk on 16/11/16.
 * 邮箱：649444395@qq.com
 */

public interface OneDoubleListener extends RequestfinishListener {
    void onSuccess(double totalPrice);

    void onError(int errorNO,String errorInfo);
}
