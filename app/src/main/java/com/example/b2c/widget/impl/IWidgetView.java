package com.example.b2c.widget.impl;

import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * 用途：
 * Created by milk on 16/8/31.
 * 邮箱：649444395@qq.com
 */
public interface IWidgetView {
    @LayoutRes
    int getRootLayoutId();

    void beforeViewBind(View view);

    void afterViewBind(View view);
}
