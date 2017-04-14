package com.example.b2c.net.listener.seller;

import com.example.b2c.net.listener.base.RequestfinishListener;

import java.util.List;

/**
 * 用途：
 * Created by milk on 16/11/1.
 * 邮箱：649444395@qq.com
 */

public interface TodayOrderListener<T> extends RequestfinishListener {
    void onSuccess(List<T> mList, String order, int buyer, int sample, int money);

    void onError(int errorNO, String errorInfo);
}
