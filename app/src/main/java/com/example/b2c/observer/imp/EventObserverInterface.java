package com.example.b2c.observer.imp;

import com.example.b2c.observer.module.BaseObserver;

/**
 * 用途：观察者的变化，在change中进行数据或者ui更新
 * 作者：Created by john on 2017/2/22.
 * 邮箱：liulei2@aixuedai.com
 */


public interface EventObserverInterface {
    public void dispatchChange(String type,BaseObserver eventType);
}
