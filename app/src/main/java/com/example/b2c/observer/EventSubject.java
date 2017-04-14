package com.example.b2c.observer;

import android.util.Log;

import com.example.b2c.observer.imp.EventSubjectInterface;
import com.example.b2c.observer.module.BaseObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;

/**
 * 用途：具体主题的实现
 * 作者：Created by john on 2017/2/22.
 * 邮箱：liulei2@aixuedai.com
 */


public class EventSubject implements EventSubjectInterface {
    public Map<String, ArrayList<EventObserver>> mEventObservers = new HashMap<>();
    public static volatile EventSubject mEventSubject;

    public static synchronized EventSubject getInstance() {
        if (mEventSubject == null) {
            mEventSubject = new EventSubject();
        }
        return mEventSubject;
    }

    @Override
    public void registerObserver(String type, EventObserver observer) {
        synchronized (type) {
            ArrayList<EventObserver> eventObservers = mEventObservers.get(type);
            if (eventObservers == null) {
                eventObservers = new ArrayList<EventObserver>();
                mEventObservers.put(type, eventObservers);
            }
            if (eventObservers.contains(observer)) {
                return;
            }
            eventObservers.add(observer);
        }
    }

    @Override
    public void removeObserver(String type, EventObserver observer) {
        synchronized (type) {
            int index = mEventObservers.get(type).indexOf(observer);
            if (index > 0) {
                mEventObservers.remove(observer);
            }
        }
    }

    @Override
    public void notifyObserver(String type, BaseObserver eventType) {
        if (mEventObservers != null && mEventObservers.size() > 0 && eventType != null) {
            ArrayList<EventObserver> eventObservers = mEventObservers.get(type);
            if (eventObservers != null) {
                for (EventObserver observer : eventObservers) {
                    observer.dispatchChange(type, eventType);
                }
            } else {
                Log.e(TAG, "eventObservers is null," + eventType + " may be not register");
            }
        }
    }
}
