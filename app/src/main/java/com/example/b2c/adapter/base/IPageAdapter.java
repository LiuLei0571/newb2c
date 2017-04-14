package com.example.b2c.adapter.base;

import java.util.Collection;

/**
 * Created by yh on 2016/7/11.
 */
public interface IPageAdapter<T> {
    /**
     * 清空数据
     */
    void clear();

    int getDataCount();

    void notifyDataSetChanged();

    boolean isDataEmpty();

    void addAll(Collection<? extends T> list);
}
