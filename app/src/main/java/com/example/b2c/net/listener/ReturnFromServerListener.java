package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;

public interface ReturnFromServerListener extends RequestfinishListener {
	void onSuccess(int errorNO, String errorInfo);

	void onError(int errorNO, String errorInfo);
}
