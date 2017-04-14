package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;

public interface LogoutListener extends RequestfinishListener {
	void onSuccess(boolean isSuccess);

	void onError(int errorNO, String errorInfo);
}
