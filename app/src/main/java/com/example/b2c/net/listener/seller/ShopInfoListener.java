package com.example.b2c.net.listener.seller;


import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.seller.ShopInfoDetail;

/**
 * 用途：
 * Created by milk on 16/10/31.
 * 邮箱：649444395@qq.com
 */

public interface ShopInfoListener extends  RequestfinishListener {
    void onSuccess(ShopInfoDetail list);

    void onError(int errorNO, String errorInfo);
}
