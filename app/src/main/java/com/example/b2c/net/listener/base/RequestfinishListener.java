package com.example.b2c.net.listener.base;

/**
 * 用途：
 * Created by milk on 16/11/6.
 * 邮箱：649444395@qq.com
 */

public interface RequestfinishListener {
    //完成回调----用于影藏加载中对话框
    void onFinish();
    //请求出错回调
    void onLose();
}
