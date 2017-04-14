package com.example.b2c.observer.imp;

import com.example.b2c.observer.EventObserver;
import com.example.b2c.observer.module.BaseObserver;

/**
 * 用途：抽象的主题角色
 * 作者：Created by john on 2017/2/22.
 * 邮箱：liulei2@aixuedai.com
 */


public interface EventSubjectInterface {
    /**
     * 注册观察者
     * @param observer
     */
    public void registerObserver(String eventType,EventObserver observer);

    /**
     * 反注册观察者
     * @param observer
     */
    public void removeObserver(String eventType,EventObserver observer);

    /**
     * 通知注册的观察者进行数据或者UI的更新
     */
    public void notifyObserver(String eventType, BaseObserver baseObserver);
}
