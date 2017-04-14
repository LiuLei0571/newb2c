package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;

/**
 * Created by Administrator on 2017/1/15.
 */

public interface RegisterListener extends RequestfinishListener {

    void onSuccess(int userId, String token, int type);

    void onError(int errorNO, String errorInfo);
}
