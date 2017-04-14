package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.SampleDetailResult;

public interface SampleDetailListener extends RequestfinishListener {
	void onSuccess(SampleDetailResult result);

	void onError(int errorNO, String errorInfo);
}
