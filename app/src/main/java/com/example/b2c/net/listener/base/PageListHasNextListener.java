package com.example.b2c.net.listener.base;

import java.util.List;

/**
 * 用途：
 * Created by milk on 16/11/1.
 * 邮箱：649444395@qq.com
 */

public interface PageListHasNextListener<T> extends RequestfinishListener {
    void onSuccess(List<T> list, boolean hasNest);

    void onError(int errorNO, String errorInfo);

}
