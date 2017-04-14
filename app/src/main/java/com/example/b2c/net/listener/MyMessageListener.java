package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.SystemMessageDetail;

import java.util.List;

public interface MyMessageListener extends RequestfinishListener {
	void onSuccess(List<SystemMessageDetail> systemMessageList);

	void onError(int errorNO, String errorInfo);
}
