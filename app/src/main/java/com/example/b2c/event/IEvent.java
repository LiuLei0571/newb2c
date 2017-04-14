package com.example.b2c.event;

/**
 * 用途：
 * 作者：Created by john on 2016/12/28.
 * 邮箱：liulei2@aixuedai.com
 */


public interface IEvent {
    void register(Object subscriber);

    void unregister(Object subscriber);

    void post(Object event);
}
