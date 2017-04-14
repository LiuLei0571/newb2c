package com.example.b2c.net.listener;

import java.util.List;


public interface ResponseHanderListener<T> {
	
	void onSuccess(List<T> list);

	void onError(int errorNO, String errorInfo);
	
	void lose();

}
