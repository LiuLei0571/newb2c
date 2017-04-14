package com.example.b2c.observer.module;

/**
 * 用途：
 * 作者：Created by john on 2017/2/22.
 * 邮箱：liulei2@aixuedai.com
 */


public class HomeObserver implements BaseObserver {
    private int position;
    private String type;
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public HomeObserver(int position, String type) {
        this.position = position;
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }
}
