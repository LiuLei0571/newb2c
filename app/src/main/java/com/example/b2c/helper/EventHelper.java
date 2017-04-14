package com.example.b2c.helper;

import de.greenrobot.event.EventBus;


/**
 * 用途：
 * 作者：Created by john on 2016/12/28.
 * 邮箱：liulei2@aixuedai.com
 */


public final class EventHelper {
    private static EventBus iEvent;

    static {
        iEvent = EventBus.getDefault();
    }

    public static void register(Object subscriber) {
        iEvent.register(subscriber);
    }

    public static void unregister(Object subscriber) {
        iEvent.unregister(subscriber);
    }

    public static void post(Object event) {
        iEvent.post(event);
    }

}
