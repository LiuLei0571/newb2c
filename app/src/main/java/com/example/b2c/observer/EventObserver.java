package com.example.b2c.observer;

import com.example.b2c.helper.ThreadHelper;
import com.example.b2c.observer.imp.EventObserverInterface;
import com.example.b2c.observer.module.BaseObserver;

/**
 * 用途：
 * 作者：Created by john on 2017/2/22.
 * 邮箱：liulei2@aixuedai.com
 */


public abstract class EventObserver implements EventObserverInterface {


    @Override
    public void dispatchChange(final String type,final BaseObserver eventType) {
        ThreadHelper.postMain(new Runnable() {
            @Override
            public void run() {
                onChange(type,eventType);
            }
        });
    }

    public abstract void onChange(String type,BaseObserver eventType);

}
