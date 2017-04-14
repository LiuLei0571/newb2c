package com.example.b2c.adapter.base;

import com.example.b2c.impl.IPageAdapter;

import java.util.Collection;

/**
 * 用途：
 * Created by milk on 16/9/1.
 * 邮箱：649444395@qq.com
 */
public abstract class BasePageAdapter<T> extends android.widget.BaseAdapter implements IPageAdapter<T> {
    public abstract void clear();

    public abstract void addAll(Collection<? extends T> datas);

}
