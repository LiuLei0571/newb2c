package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.GetShopHomeResult;

public interface GetShopHomeListener extends RequestfinishListener {
	
	void onSuccess(GetShopHomeResult result);
	
	void onError(int errorNO, String errorInfo);
	
}
