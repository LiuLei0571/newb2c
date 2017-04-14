package com.example.b2c.impl;

import java.util.Collection;

/**
 * 用途：基础adapter接口
 * Created by milk on 16/9/1.
 * 邮箱：649444395@qq.com
 */
public interface IPageAdapter<T>{
    void clear();
    void addAll(Collection<? extends T> datas);
    int getCount();
    void notifyDataSetChanged();
    boolean isEmpty();
}
