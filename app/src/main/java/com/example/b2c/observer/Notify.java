package com.example.b2c.observer;

import com.example.b2c.observer.module.BaseObserver;

/**
 * 用途：通知类
 * 作者：Created by john on 2017/2/22.
 * 邮箱：liulei2@aixuedai.com
 */


public class Notify {
    private static volatile Notify mNotify;

    public static Notify getInstance() {
        if (mNotify == null) {
            mNotify = new Notify();
        }
        return mNotify;
    }

    public void NotifyActivity(String type,BaseObserver eventType) {
        EventSubject eventSubject = EventSubject.getInstance();
        EventType eventTypes = EventType.getInstance();
        if (eventTypes.contains(type)) {
            eventSubject.notifyObserver(type,eventType);
        }
    }
}
