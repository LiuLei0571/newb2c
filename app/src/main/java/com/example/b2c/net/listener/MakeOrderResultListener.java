package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.MakeOrderResult;

/**
 * 立即购买确认订单，生成订单listener
 */
public interface MakeOrderResultListener extends RequestfinishListener {
	
	void onSuccess(MakeOrderResult result,String errorInfo);

	void onError(int errorNO, String errorInfo);
	

}
