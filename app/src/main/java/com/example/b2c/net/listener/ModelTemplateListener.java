package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.ModelPage;

import java.util.List;

/**
 * 用途：
 * Created by milk on 17/2/3.
 * 邮箱：649444395@qq.com
 */

public interface ModelTemplateListener extends RequestfinishListener {
    void onSuccess(List<ModelPage> modelPageList, String msg);

    void onError(int errorNO, String errorInfo);

}
