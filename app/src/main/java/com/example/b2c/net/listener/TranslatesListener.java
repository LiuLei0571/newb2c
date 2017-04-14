package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.translate.Translates;

public interface TranslatesListener extends RequestfinishListener {
	void onSuccess(Translates translates);

	void onError(int errorNO, String errorInfo);
}
