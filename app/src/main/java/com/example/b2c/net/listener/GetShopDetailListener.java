package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.GetShopDetailResult;

public interface GetShopDetailListener extends RequestfinishListener {
	void onSuccess(GetShopDetailResult result);

	void onError(int errorNO, String errorInfo);
}
