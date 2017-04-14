package com.example.b2c.observer.module;

/**
 * 用途：
 * 作者：Created by john on 2017/3/3.
 * 邮箱：liulei2@aixuedai.com
 */


public class GoodsSpecificationObserver implements BaseObserver {
    public boolean isShow;
    public String type;

    public GoodsSpecificationObserver(boolean isShow ) {
        this.isShow = isShow;
    }

    @Override
    public String getType() {
        return "";
    }
}
