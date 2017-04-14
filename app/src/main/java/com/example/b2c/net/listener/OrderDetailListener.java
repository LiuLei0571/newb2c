package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.OrderDetailResult;

public interface OrderDetailListener extends RequestfinishListener {
	void onSuccess(OrderDetailResult result);

	void onError(int errorNO, String errorInfo);
}
