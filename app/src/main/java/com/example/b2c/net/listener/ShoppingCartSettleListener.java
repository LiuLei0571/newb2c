package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.MakeOrderResult;

/**
 * 购物车下单listener
 */
public interface ShoppingCartSettleListener extends RequestfinishListener {
	
	void onSuccess(int errorNO,String errorInfo);
	
	void onError(int errorNO, String errorInfo);
}
