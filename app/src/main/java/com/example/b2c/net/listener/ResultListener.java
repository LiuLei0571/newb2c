package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;

/**
 * 用途：
 * Created by milk on 16/11/1.
 * 邮箱：649444395@qq.com
 */

public interface ResultListener extends RequestfinishListener {
    void onSuccess(String errorInfo);
    void onError(int errorNO, String errorInfo);
}
