package com.example.b2c.net.listener;


import com.example.b2c.net.listener.base.RequestfinishListener;

import java.util.List;

public interface UploadListener extends RequestfinishListener {
	void onSuccess(List<String> path);

	void onError(int errorNO, String errorInfo);
}
