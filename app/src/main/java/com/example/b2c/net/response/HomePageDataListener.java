package com.example.b2c.net.response;

import com.example.b2c.net.listener.base.RequestfinishListener;

/**
 * 用途：
 * Created by milk on 16/11/8.
 * 邮箱：649444395@qq.com
 */

public interface HomePageDataListener extends RequestfinishListener {
    void onSuccess(HomePageDataObject homePageData, String errorInfo);

    void onError(int errotNo,String errorInfo);
}
