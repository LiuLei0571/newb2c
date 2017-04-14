package com.example.b2c.helper;

import com.example.b2c.observer.Notify;
import com.example.b2c.observer.module.BaseObserver;

/**
 * 用途：
 * 作者：Created by john on 2017/2/22.
 * 邮箱：liulei2@aixuedai.com
 */


public class NotifyHelper {
    public static Notify notify = Notify.getInstance();

    public static void setNotifyData(String type, BaseObserver eventType) {
        notify.NotifyActivity(type, eventType);
    }
}
