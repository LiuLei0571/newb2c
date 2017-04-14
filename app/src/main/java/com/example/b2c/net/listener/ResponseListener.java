package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;

public interface ResponseListener extends RequestfinishListener {
	void onSuccess(String errorInfo);

	void onError(int errorNO, String errorInfo);
}
