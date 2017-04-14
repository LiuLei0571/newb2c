package com.example.b2c.net.listener.base;

/**
 * 用途：
 * Created by milk on 16/11/13.
 * 邮箱：649444395@qq.com
 */

public interface OneDataListener<T> extends RequestfinishListener {
    void onSuccess(T data, String successInfo);
    void onError(int errorNO,String errorInfo);
}
