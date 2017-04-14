package com.example.b2c.event;


import de.greenrobot.event.EventBus;

/**
 * 用途：
 * 作者：Created by john on 2016/12/28.
 * 邮箱：liulei2@aixuedai.com
 */


public class EventBusImpl implements IEvent {
    private EventBus eventBus;

    public EventBusImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void register(Object subscriber) {
        eventBus.register(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    @Override
    public void post(Object event) {
        eventBus.post(event);
    }
}
