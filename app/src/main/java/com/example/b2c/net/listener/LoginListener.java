package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;

public interface LoginListener  extends RequestfinishListener {
	void onSuccess(int userId, String token, String type,int step);
	void onError(int errorNO, String errorInfo);
}
