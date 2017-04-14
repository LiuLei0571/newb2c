package com.example.b2c.observer;

import java.util.HashSet;
import java.util.Set;

/**
 * 用途：
 * 作者：Created by john on 2017/2/22.
 * 邮箱：liulei2@aixuedai.com
 */


public class EventType {
    private static volatile EventType mEventType;
    private final Set<String> mTyps = new HashSet<>();

    public EventType() {
        mTyps.addAll(ObserverTypes.getValue());
    }

    public static EventType getInstance() {
        if (mEventType == null) {
            mEventType = new EventType();
        }
        return mEventType;
    }

    public boolean contains(String eventType) {
        return mTyps.contains(eventType);
    }
}
